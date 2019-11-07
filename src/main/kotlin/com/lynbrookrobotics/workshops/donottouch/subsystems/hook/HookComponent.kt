package com.lynbrookrobotics.workshops.donottouch.subsystems.hook

import com.lynbrookrobotics.workshops.donottouch.subsystems.Component

class HookComponent(hardware: HookHardware) : Component<HookComponent, HookHardware, Boolean>(hardware) {

    override val fallbackController: HookComponent.() -> Boolean = { false }

    override fun HookHardware.output(value: Boolean) {
        solenoid.set(value)
    }

}