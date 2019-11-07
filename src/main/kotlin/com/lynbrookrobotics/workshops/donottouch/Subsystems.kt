package com.lynbrookrobotics.workshops.donottouch

import com.lynbrookrobotics.workshops.donottouch.subsystems.hook.HookComponent
import com.lynbrookrobotics.workshops.donottouch.subsystems.hook.HookHardware
import com.lynbrookrobotics.workshops.donottouch.subsystems.lift.LiftComponent
import com.lynbrookrobotics.workshops.donottouch.subsystems.lift.LiftHardware
import com.lynbrookrobotics.workshops.donottouch.subsystems.slider.SliderComponent
import com.lynbrookrobotics.workshops.donottouch.subsystems.slider.SliderHardware
import edu.wpi.first.wpilibj.Joystick

class Subsystems {

    val lift = LiftComponent(LiftHardware())
    val hook = HookComponent(HookHardware())
    val slider = SliderComponent(SliderHardware())
    val joystick = Joystick(0)

    init {
        EventLoop.runOnTick {
            lift.loop()
            hook.loop()
            slider.loop()
        }
    }

}