package com.lynbrookrobotics.workshops.donottouch.subsystems.slider

import com.lynbrookrobotics.workshops.donottouch.subsystems.Component

class SliderComponent(hardware: SliderHardware) : Component<SliderComponent, SliderHardware, Boolean>(hardware) {

    override val fallbackController: SliderComponent.() -> Boolean = { false }

    override fun SliderHardware.output(value: Boolean) {
        solenoid.set(value)
    }

}