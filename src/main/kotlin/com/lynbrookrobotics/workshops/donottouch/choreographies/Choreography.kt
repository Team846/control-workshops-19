package com.lynbrookrobotics.workshops.donottouch.choreographies

import kotlinx.coroutines.*

/**
 * Make a new [Choreography] in a DSL-like manner.
 *
 * @param name of the choreography.
 * @param setup choreography builder.
 */
suspend fun choreography(name: String, setup: Choreography.() -> Unit) = Choreography(name).apply(setup).invoke()

/**
 * A sequence of routines from various subsystems.
 *
 * A choreography is a wrapper for two blocks to run in a [coroutineScope] which allows logging and exception handling.
 *
 * @param name of the choreography used for logging.
 */
class Choreography internal constructor(
        private val name: String
) {

    private var onStart: Block = block {}
    private var onEnd: Block? = null

    fun onStart(block: Block) {
        onStart = block
    }

    fun onEnd(block: Block) {
        onEnd = block {
            this.block()
            this.cancel()
        }
    }

    /**
     * In a new [coroutineScope], run the [onStart] block, logging cancellation or other exceptions/errors.
     * After the [onStart] block completes or cancels, run the [onEnd] block in the same scope.
     *
     * Once this is invoked once, the sensors are closed and following invokes will immediately return.
     */
    suspend operator fun invoke() {
        coroutineScope {
            try {
                println("Started $name choreography start.")
                try {
                    onStart()
                } catch (c: CancellationException) {
                    // Don't run onEnd block if onStart is cancelled
                    throw c
                }

                try {
                    freeze()
                } finally {
                    onEnd?.let {
                        withContext(NonCancellable) {
                            println("Started $name choreography end.")
                            it()
                            println("Completed $name choreography end.")
                        }
                    }
                }

                println("Completed $name choreography start.")
            } catch (c: CancellationException) {
                println("Cancelled $name choreography start.\n${c.message}")
                throw c
            }
        }
    }

}