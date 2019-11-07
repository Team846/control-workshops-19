package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.subsystems.hook.HookComponent
import com.lynbrookrobotics.workshops.donottouch.subsystems.lift.LiftComponent
import com.lynbrookrobotics.workshops.donottouch.subsystems.slider.SliderComponent
import info.kunalsheth.units.generated.DutyCycle
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Length
import info.kunalsheth.units.generated.Percent

suspend fun LiftComponent.set(target: DutyCycle) = startRoutine {
    controller { target }
}

suspend fun LiftComponent.set(target: Length) = startRoutine {

    val kP = 10.0

    controller {
        (kP * (target - position).Inch).Percent
    }
}

suspend fun HookComponent.set(target: Boolean) = startRoutine {
    controller { target }
}

suspend fun SliderComponent.set(target: Boolean) = startRoutine {
    controller { target }
}