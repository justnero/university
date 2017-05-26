package visualization

import bellman.test.ParallelAndSequentialTime
import bellman.test.ResultSet
import golem.*

val figureColors = mutableMapOf(0 to plotColors.iterator())

fun getNextColor(figure: Int): String {
    if (!figureColors.contains(figure) || !figureColors[figure]!!.hasNext()) {
        figureColors.put(figure, plotColors.iterator())
    }

    return figureColors[figure]!!.next().key
}

fun allEdgeProbabilityPlot(makeTest: ResultSet) {
    val x = makeTest.results.map { it.input.vertexNumber }.distinct().toIntArray()
    val groupByEdge = makeTest.results.groupBy { it.input.edgeProbability }
    var seqPar = 2
    val comparisonNumber = 2 + groupByEdge.keys.count() + 1

    groupByEdge.forEach { edgeProbability, u ->
        allEdgeProbabilityPlot(x, u.map { it.millisecondTime.second }.toDoubleArray(), edgeProbability, 0)
        allEdgeProbabilityPlot(x, u.map { it.millisecondTime.first }.toDoubleArray(), edgeProbability, 1, true)

        sequentialAndParallelPlot(x, u.map { it.millisecondTime }, edgeProbability, seqPar)
        sequentialAndParallelComparisonPlot(x, u.map { it.millisecondTime }, edgeProbability, comparisonNumber)
        seqPar++
    }
}


fun allEdgeProbabilityPlot(x: IntArray, y: DoubleArray, edgeProbability: Double, figure: Int, isParallel: Boolean = false) {
    figure(figure)
    plot(x, y, getNextColor(figure), "разяженность графа: $edgeProbability")

    ylabel("Время, мс")
    xlabel("Количество вершин")
    title("${if (isParallel) "Параллельный" else "Последовательный"} алгоритм")
}

fun sequentialAndParallelPlot(x: IntArray, y: List<ParallelAndSequentialTime>, edgeProbability: Double, figure: Int) {
    figure(figure)
    plot(x, y.map { it.first }.toDoubleArray(), getNextColor(figure), "параллельный алгоритм")
    plot(x, y.map { it.second }.toDoubleArray(), getNextColor(figure), "последовательный алгоритм")

    ylabel("Время, мс")
    xlabel("Количество вершин")
    title("Последовательный и параллельный алгоритм при разряженности графа: $edgeProbability")
}

fun sequentialAndParallelComparisonPlot(x: IntArray, y: List<ParallelAndSequentialTime>, edgeProbability: Double, figure: Int) {
//    figure(figure)
//    plot(x, y.map { it.first }.toDoubleArray(),
//            getNextColor(figure), "Параллельный: $edgeProbability")
//    plot(x, y.map { it.second }.toDoubleArray(),
//            getNextColor(figure), "Последовательный: $edgeProbability")
//
//    ylabel("Время, мс")
//    xlabel("Количество вершин")
//    title("Отношение параллельного к последовательному")
}