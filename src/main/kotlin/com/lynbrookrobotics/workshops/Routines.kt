package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.subsystems.Leds
import com.lynbrookrobotics.workshops.donottouch.timing.currentTime
import info.kunalsheth.units.generated.*
import java.awt.Color
import kotlin.math.abs
import kotlin.math.sin

suspend fun Leds.set(target: Color) = startRoutine("Leds set") {
    controller { target }
}

suspend fun Leds.fadeRed() = startRoutine("Leds fade red") {

    controller {
        val red = abs(sin(currentTime.Second) * 255.0).toInt()
        Color(red, 0, 0)
    }
}

suspend fun Leds.rainbow() = startRoutine("Leds fade rainbow") {

    controller {
        val hue = (currentTime.Second % 255).toFloat()

        Color.getHSBColor(hue, 1.0f, 1.0f)
    }

}