package bellman.graph.algorithm

import bellman.graph.INFINITE
import bellman.graph.InputGraph
import bellman.graph.PlainAdjacency
import bellman.graph.PlainAdjacencyList
import bellman.graph.Util.AdjacencyMatrixUtil.toPlainAdjacencyList
import bellman.graph.Util.PlainAdjacencyListUtil.edgeNumber
import bellman.graph.Util.PlainAdjacencyListUtil.get

fun bellmanFord(graph: InputGraph) = with(graph) {
    bellmanFord(
            adjacencyMatrix.toPlainAdjacencyList(),
            sourceVertex,
            vertexNumber)
}


fun bellmanFord(plainAdjacencyList: PlainAdjacencyList,
                sourceVertex: Int,
                vertexNumber: Int): IntArray =
        IntArray(vertexNumber, { INFINITE })
                .apply { this[sourceVertex] = 0 }
                .apply { while (plainAdjacencyList.relaxAll(this)); }

//TODO:Need optimization
fun PlainAdjacencyList.relaxAll(distance: IntArray, from: Int = 0, to: Int = edgeNumber - 1) =
        (from..to)
                .map { relax(it, distance) }
                .onEach { if (it) return@relaxAll true }
                .let { false }


fun PlainAdjacencyList.relax(index: Int, distance: IntArray): Boolean {
    val lastValue = distance[get(index, PlainAdjacency.DESTINATION)]

    if (distance[get(index, PlainAdjacency.SOURCE)] < INFINITE) {
        distance[get(index, PlainAdjacency.DESTINATION)] =
                minOf(distance[get(index, PlainAdjacency.DESTINATION)].toLong(),
                        distance[get(index, PlainAdjacency.SOURCE)].toLong()
                                + get(index, PlainAdjacency.WEIGHT))
                        .toInt()
    }

    val isRelaxed = lastValue != distance[get(index, PlainAdjacency.DESTINATION)]

    return isRelaxed
}
