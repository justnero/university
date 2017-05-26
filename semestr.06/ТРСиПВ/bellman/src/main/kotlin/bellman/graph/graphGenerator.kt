package bellman.graph

import bellman.graph.Util.AdjacencyMatrixUtil.toPlainAdjacencyList

private operator fun AdjacencyMatrix.set(range: IntRange, value: Int) =
        range.forEach { this[it][it] = value }

fun plainAdjacencyList(vertexNumber: Int,
                       edgeProbability: Double,
                       maxWeight: Int = INFINITE / (vertexNumber * vertexNumber)): PlainAdjacencyList =
        adjacencyMatrix(vertexNumber, edgeProbability, maxWeight)
                .toPlainAdjacencyList()

fun adjacencyMatrix(vertexNumber: Int,
                    edgeProbability: Double = 0.5,
                    maxWeight: Int = INFINITE / (vertexNumber * (vertexNumber + 1))): AdjacencyMatrix =
        Array(vertexNumber, { IntArray(vertexNumber, { generateWeight(edgeProbability, maxWeight) }) })
                .apply { this[0..vertexNumber - 1] = 0 }


private fun generateWeight(edgeProbability: Double, maxWeight: Int) =
        if (Math.random() <= edgeProbability) random(maxWeight) else NO_EDGE

fun random(maxValue: Int) = (1 + Math.random() * maxValue).toInt()


