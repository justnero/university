package bellman.graph.algorithm

import bellman.graph.Adjacency
import bellman.graph.AdjacencyList
import bellman.graph.INFINITE
import bellman.graph.Util.AdjacencyUtil.destination
import bellman.graph.Util.AdjacencyUtil.source
import bellman.graph.Util.AdjacencyUtil.weight

fun bellmanFord(adjacencyList: AdjacencyList,
                sourceVertex: Int,
                vertexNumber: Int,
                edgeNumber: Int = adjacencyList.size): IntArray {

    val distance = IntArray(vertexNumber, { INFINITE }).apply { this[sourceVertex] = 0 }

    for (i in 0..vertexNumber - 1) {
        if (!adjacencyList.relaxAll(distance))
            return distance
    }

    return distance
}

inline fun AdjacencyList.relaxAll(distance: IntArray, from: Int = 0, to: Int = lastIndex) =
        (from..to).map { get(it).relax(distance) }.onEach { if (it) return@relaxAll true }.let { false }


fun Adjacency.relax(distance: IntArray): Boolean {
    val lastValue = distance[destination]

    if (distance[source] < INFINITE) {
        distance[destination] =
                minOf(distance[destination].toLong(), distance[source].toLong() + weight).toInt()
    }

    val isRelaxed = lastValue != distance[destination]

    return isRelaxed
}
