import bellman.test.ResultSet
import bellman.test.makeTest
import bellman.test.testSet
import bellman.test.toJson
import mpi.MPI
import visualization.allEdgeProbabilityPlot
import kotlin.system.measureNanoTime

fun rank(args: Array<String>): Int {
//    MPI.Init(args)
    val rank = MPI.COMM_WORLD.Rank()
//    MPI.Finalize()

    return rank
}

fun main(args: Array<String>) {
    MPI.Init(args)
    val rank = rank(args)

    var makeTest = ResultSet(listOf())

    val measureNanoTime = measureNanoTime { makeTest = makeTest(args, testSet("config.json")) }
    println(measureNanoTime / 1e9)

    if (rank == 0) {
        allEdgeProbabilityPlot(makeTest)
        println(makeTest.toJson().toJsonString(true))

    }
    MPI.Finalize()
}