package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.lynbrookrobotics.workshops.donottouch.Component
import edu.wpi.first.wpilibj.Solenoid

class Slider : Component<Slider, Boolean>() {

    private val solenoid = Solenoid(1)

    override val fallbackController: Slider.() -> Boolean = { false }

    override fun output(value: Boolean) {
        solenoid.set(value)
    }

}