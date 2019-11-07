package com.lynbrookrobotics.workshops.donottouch

import com.lynbrookrobotics.workshops.donottouch.ExecutionOrder.First
import com.lynbrookrobotics.workshops.donottouch.ExecutionOrder.Last
import com.lynbrookrobotics.workshops.donottouch.subsystems.blockingMutex

enum class ExecutionOrder {
    First, Last
}

class Cancel(private val f: () -> Unit) {
    fun cancel() = f()
}

object EventLoop {

    private val jobsToRun = mutableListOf<() -> Unit>()
    private val jobsToKill = mutableSetOf<() -> Unit>()

    fun runOnTick(order: ExecutionOrder = First, run: () -> Unit): Cancel {
        blockingMutex(jobsToRun) {
            when (order) {
                First -> jobsToRun.add(0, run)
                Last -> jobsToRun += run
            }
        }
        return Cancel { blockingMutex(jobsToKill) { jobsToKill += run } }
    }

    fun tick() = blockingMutex(jobsToRun) {
        // avoid deadlocks
        if (jobsToKill.isNotEmpty()) blockingMutex(jobsToKill) {
            jobsToRun -= jobsToKill
            jobsToKill.clear()
        }

        jobsToRun.forEach { it() }
    }
}