package bellman.graph.algorithm.parallel

import mpi.Datatype
import mpi.MPI

data class EdgeSegment(val startEdge: Int, val endEdge: Int)

fun Slave.EdgeSegment(edgeNumber: Int = this.edgeNumber): EdgeSegment {
    val edgePerProc = edgeNumber / procNum
    val startEdge = edgePerProc * rank
    val endEdge = if (rank != procNum - 1) startEdge + edgePerProc - 1 else edgeNumber - 1

    return EdgeSegment(startEdge, endEdge)
}

fun mpiBcastOneValue(value: Int, datatype: Datatype?, root: Int): Int {
    val wrapper = intArrayOf(value)
    MPI.COMM_WORLD.Bcast(wrapper, 0, 1, datatype, root)
    return wrapper[0]
}
