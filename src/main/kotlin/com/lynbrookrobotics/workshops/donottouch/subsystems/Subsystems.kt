package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import edu.wpi.first.wpilibj.Joystick

class Subsystems {

    val lift = Lift()
    val hook = Hook()
    val slider = Slider()
    val joystick = Joystick(0)

    init {
        EventLoop.runOnTick {
            lift.loop()
            hook.loop()
            slider.loop()
        }
    }

}