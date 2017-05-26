package bellman.graph.algorithm.parallel

import mpi.MPI
import bellman.graph.*
import bellman.graph.Util.AdjacencyMatrixUtil.toPlainAdjacencyList
import bellman.graph.Util.PlainAdjacencyListUtil.edgeNumber
import bellman.graph.algorithm.relaxAll
import bellman.test.GenerateValues
import bellman.test.generateGraph
import kotlin.properties.Delegates

class Master(override var vertexNumber: Int,
             override var sourceVertex: Int,
             override var plainAdjacencyList: PlainAdjacencyList) : Slave() {
    override val isRoot = true
    override var edgeNumber: Int = -1
        get() = plainAdjacencyList.edgeNumber

    constructor(graph: InputGraph) : this(graph.vertexNumber, graph.sourceVertex, graph.adjacencyMatrix)

    constructor(vertexNumber: Int, sourceVertex: Int, adjacencyMatrix: AdjacencyMatrix)
            : this(vertexNumber, sourceVertex, adjacencyMatrix.toPlainAdjacencyList())

    constructor(graph: GenerateValues)
            : this(graph.vertexNumber, random(graph.vertexNumber - 1), graph.generateGraph())
}

open class Slave : AbstractProcess() {
    override var vertexNumber: Int = -1
    override var edgeNumber: Int = -1
    override var sourceVertex: Int = 0
    override var plainAdjacencyList by Delegates.notNull<PlainAdjacencyList>()

    override val isRoot = false

    override fun mainWork() {
        var hasRelax = true

        for (i in 0..vertexNumber - 1) {
            hasRelax = plainAdjacencyList.relaxAll(distance, edgeSegment.startEdge, edgeSegment.endEdge)
            MPI.COMM_WORLD.Allreduce(distance, 0, temp, 0, vertexNumber, MPI.INT, MPI.MIN)

            if (!hasRelax and (distance contentEquals temp)) break

            distance = temp.clone()
        }
    }

    override fun preparation() {
        vertexNumber = mpiBcastOneValue(vertexNumber, MPI.INT, 0)
        edgeNumber = mpiBcastOneValue(edgeNumber, MPI.INT, 0)
        sourceVertex = mpiBcastOneValue(sourceVertex, MPI.INT, 0)

        try {
            distance.size
        } catch (e: IllegalStateException) {
            firstPreparation()
        }

        distance[sourceVertex] = 0
        MPI.COMM_WORLD.Bcast(plainAdjacencyList, 0, 3 * edgeNumber, MPI.INT, 0)
        edgeSegment = EdgeSegment()
    }

    protected open fun firstPreparation() {
        distance = IntArray(vertexNumber, { INFINITE })
        temp = IntArray(vertexNumber)

        if (!isRoot) plainAdjacencyList = PlainAdjacencyList(3 * vertexNumber * vertexNumber)
    }
}

abstract class AbstractProcess {
    abstract var vertexNumber: Int
    abstract var edgeNumber: Int
    abstract var sourceVertex: Int

    abstract var plainAdjacencyList: PlainAdjacencyList

    abstract val isRoot: Boolean
    var distance by Delegates.notNull<IntArray>()

    val rank: Int = MPI.COMM_WORLD.Rank()
    val procNum = MPI.COMM_WORLD.Size()

    var edgeSegment by Delegates.notNull<EdgeSegment>()
    protected var temp by Delegates.notNull<IntArray>()

    fun work() {
        preparation()
        mainWork()
    }

    open fun reset() {
        distance.fill(INFINITE)
        temp.fill(0)
    }

    abstract protected fun mainWork()

    abstract protected fun preparation()
}
