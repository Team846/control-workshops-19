package com.lynbrookrobotics.workshops.donottouch.timing

import com.lynbrookrobotics.workshops.donottouch.util.blockingMutex
import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop.ExecutionOrder.First
import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop.ExecutionOrder.Last

object EventLoop {

    private val jobsToRun = mutableListOf<() -> Unit>()
    private val jobsToKill = mutableSetOf<() -> Unit>()

    enum class ExecutionOrder {
        First, Last
    }

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