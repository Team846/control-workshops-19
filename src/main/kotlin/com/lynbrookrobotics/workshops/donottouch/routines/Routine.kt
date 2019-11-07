package com.lynbrookrobotics.workshops.donottouch.routines

import com.lynbrookrobotics.workshops.donottouch.subsystems.Component
import com.lynbrookrobotics.workshops.donottouch.subsystems.SubsystemHardware
import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Represents an active subsystem routine
 *
 * Routines run until their controller returns null, they throw an exception, or they are cancelled.
 *
 * @author Kunal
 * @see Component
 *
 * @param C type of this subsystem's component
 * @param H type of this subsystem's hardware
 * @param Output type of this subsystem's output
 */
class Routine<C, H, Output> internal constructor(
        private val controller: C.() -> Output?,
        cont: CancellableContinuation<Unit>
) :
        CancellableContinuation<Unit> by cont,
        (C) -> Output

        where C : Component<C, H, Output>,
              H : SubsystemHardware<H, C> {

    /**
     * Calculate the next subsystem output and manage this routine's lifecycle
     *
     * @param c this subsystem's component
     * @param t control loop start time
     * @return next subsystem output
     */
    override fun invoke(c: C) =
            try {
                controller(c) ?: c.fallbackController(c).also { resume(Unit) }
            } catch (e: Throwable) {
                resumeWithException(e)
                c.fallbackController(c)
            }
}