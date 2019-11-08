package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.math.differentiator
import com.lynbrookrobotics.workshops.donottouch.subsystems.Hook
import com.lynbrookrobotics.workshops.donottouch.subsystems.Lift
import com.lynbrookrobotics.workshops.donottouch.subsystems.Slider
import com.lynbrookrobotics.workshops.donottouch.timing.currentTime
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.`±`

suspend fun Lift.set(target: DutyCycle) = startRoutine("Lift Set") {
    controller { target }
}

suspend fun Lift.set(target: Length, threshold: Length) = startRoutine("Lift Set Position") {

    val dxdt = differentiator(::div, currentTime, position)

    val kP = 100.Percent / 3.Inch
    val kD = 50.Percent / 3.FootPerSecond

    controller {
        val error = target - position
        val velocity = dxdt(currentTime, position)
        (kP * error - kD * velocity).takeUnless { position in target `±` threshold }
    }
}

suspend fun Hook.set(target: Boolean) = startRoutine("Hook Set") {
    controller { target }
}

suspend fun Slider.set(target: Boolean) = startRoutine("Hook Slider Set") {
    controller { target }
}