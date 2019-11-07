package com.lynbrookrobotics.workshops.donottouch.timing

/**
 * Stops some previously setup behavior
 *
 * @author Kunal
 * @see Clock
 *
 * @param f function to wrap
 */
class Cancel(private val f: () -> Unit) {
    fun cancel() = f()
}