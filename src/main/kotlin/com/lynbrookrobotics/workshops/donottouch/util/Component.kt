package com.lynbrookrobotics.workshops.donottouch.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

inline fun <R> blockingMutex(lock: Any, block: () -> R) = synchronized(lock, block)

/**
 * Represents a robot subsystem's operations.
 *
 * Intended for writing autonomous routines, running control loops, and outputting to hardware.
 *
 * @author Kunal
 * @see SubsystemHardware
 * @see pref
 * @see Ticker
 *
 * @param This type of child class
 * @param Output type of this subsystem's output
 *
 * @param hardware this subsystem's hardware
 */
abstract class Component<This, Output> where This : Component<This, Output> {

    @Suppress("UNCHECKED_CAST")
    private val thisAsThis = this as This

    /**
     * controller to use when no `routine` is running.
     */
    abstract val fallbackController: This.() -> Output

    /**
     * actively running routine
     */
    var routine: Routine<This, Output>? = null
        private set(value) = blockingMutex(this) {
            field.takeUnless { it === value }?.cancel()
            field = value
        }
        get() = field?.takeIf { it.isActive }

    /**
     * Setup and run a new routine
     *
     * After setup, the routine runs until it crashes, is cancelled, or its controller returns `null`.
     *
     * @param name logging name
     * @param setup function returning a subsystem controller
     */
    suspend fun startRoutine(
            name: String,
            setup: () -> This.() -> Output?
    ) {
        try {
            println("Starting $name routine")
            val controller = setup()
            suspendCancellableCoroutine<Unit> { cont ->
                Routine(controller, cont).also {
                    this.routine = it
                }
            }
            println("Completed $name routine")
        } catch (c: CancellationException) {
            println("Cancelled $name routine")
            throw c
        }
    }

    /**
     * Utility function to create a new subsystem controller
     *
     * @receiver this subsystem's component
     * @param Time loop start time
     * @return value to write to hardware or `null` to end the routine
     */
    fun controller(controller: This.() -> Output?) = controller

    /**
     * Write the `value` to the hardware
     *
     * This function should be as low-level as possible. Besides safeties, there should never be any extra control code inside this function.
     *
     * @receiver this subsystem's hardware
     * @param value output to write
     */
    protected abstract fun output(value: Output)

    fun loop() {
        try {
            @Suppress("UNNECESSARY_SAFE_CALL")
            (routine ?: fallbackController)
                    ?.invoke(thisAsThis)
                    ?.let { output(it) }
        } catch (t: Throwable) {
            routine?.resumeWithException(t) ?: println(t)
        }
    }
    
}