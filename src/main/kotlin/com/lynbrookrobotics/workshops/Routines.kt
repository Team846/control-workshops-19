package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.subsystems.Hook
import com.lynbrookrobotics.workshops.donottouch.subsystems.Lift
import com.lynbrookrobotics.workshops.donottouch.subsystems.Slider
import info.kunalsheth.units.generated.DutyCycle
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Length
import info.kunalsheth.units.generated.Percent

suspend fun Lift.set(target: DutyCycle) = startRoutine("Lift Set") {
    controller { target }
}

suspend fun Lift.set(target: Length) = startRoutine("Lift Set Position") {

    val kP = 10.0

    controller {
        (kP * (target - position).Inch).Percent
    }
}

suspend fun Hook.set(target: Boolean) = startRoutine("Hook Set") {
    controller { target }
}

suspend fun Slider.set(target: Boolean) = startRoutine("Hook Slider Set") {
    controller { target }
}