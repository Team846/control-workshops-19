package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.lynbrookrobotics.workshops.donottouch.util.Component
import edu.wpi.first.wpilibj.Solenoid

class Hook : Component<Hook, Boolean>() {

    private val solenoid = Solenoid(2)

    override val fallbackController: Hook.() -> Boolean = { false }

    override fun output(value: Boolean) {
        solenoid.set(value)
    }

}