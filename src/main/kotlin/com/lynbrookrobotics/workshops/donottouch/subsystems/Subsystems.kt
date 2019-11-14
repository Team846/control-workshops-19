package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import edu.wpi.first.wpilibj.Joystick

class Subsystems {

    val leds = Leds()
    val joystick = Joystick(0)

    init {
        EventLoop.runOnTick {
            leds.loop()
        }
    }

}