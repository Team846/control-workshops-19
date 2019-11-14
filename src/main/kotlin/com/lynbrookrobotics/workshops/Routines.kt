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

suspend fun Hook.set(target: Boolean) = startRoutine("Hook Set") {
    controller { target }
}

suspend fun Slider.set(target: Boolean) = startRoutine("Hook Slider Set") {
    controller { target }
}

// CHALLENGE #1/2/3
suspend fun Lift.set(target: Length /* Extra parameters here */) = startRoutine("Lift Position Set") {

    // Initialization code here (run only once) e.g. variables

    controller {
        // Control code here
        // The last line of code in this "controller" block is the output you want to set (Percent)

        0.Percent // Placeholder output
    }

}