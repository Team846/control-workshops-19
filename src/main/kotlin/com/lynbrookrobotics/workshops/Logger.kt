package com.lynbrookrobotics.workshops

import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.kilo
import java.io.File
import java.nio.charset.StandardCharsets.US_ASCII
import kotlin.concurrent.thread

private val writer = File("/tmp/week_six_log.csv")
        .printWriter(US_ASCII)
        .apply {
            println("Time (seconds),Battery (volts),Position (kiloticks),Speed (percent),Output (percent)")
            Runtime.getRuntime().addShutdownHook(
                    thread(start = false, block = this::close)
            )
        }

fun log(
        time: Time,
        batteryVoltage: V,
        position: Dimensionless,
        speed: Frequency,
        output: DutyCycle
) {
    writer.println("${
    time.Second},${
    batteryVoltage.Volt},${
    position.kilo(Each)},${
    (speed / FunkyRobot.topSpeed).Percent},${
    output.Percent}")
}

fun flushLog() = writer.flush()