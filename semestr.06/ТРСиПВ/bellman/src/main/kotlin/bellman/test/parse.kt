package bellman.test

import com.beust.klaxon.*
import java.io.File


fun String.getFileFromResources() = File(Thread.currentThread().contextClassLoader.getResource(this).file)

fun testSet(fileName: String = "config.json"): TestSet = TestSet(listOf(testTask(fileName)))

fun testTask(fileName: String = "config.json"): TestTask
        = (Parser().parse(fileName.getFileFromResources().path) as JsonObject).testTask()

fun JsonObject.testTask() = TestTask(
        int("iterationCoefficient")!!,
        generateGraphConfiguration("graph")
)

fun JsonObject.generateGraphConfiguration() = GenerateGraphConfiguration(
        array<Int>("vertexNumber")?.toIntArray()!!,
        array<Double>("edgeProbability")?.toDoubleArray()!!
)

fun JsonObject.generateGraphConfiguration(fieldName: String) =
        obj(fieldName)!!.generateGraphConfiguration()


fun ResultSet.toJson() =
        json {
            array(
                    results
                            .mapIndexed { index, testResult ->
                                index.toString() to testResult.toJson()
                            }
                            .map { obj(it) }
            )
        }

fun TestResult.toJson() =
        json {
            obj(
                    "processNumber" to processNumber,
                    "input" to input.toJson(),
                    "millisecondTime" to millisecondTime.toJson()
            )
        }

fun Input.toJson() =
        json {
            obj(
                    "iterationNumber" to iterationNumber,
                    "vertexNumber" to vertexNumber,
                    "edgeProbability" to edgeProbability
            )
        }

fun ParallelAndSequentialTime.toJson() =
        json {
            obj(
                    "parallel" to first,
                    "sequential" to second
            )
        }