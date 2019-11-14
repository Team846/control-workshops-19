package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.ctre.phoenix.CANifier
import com.lynbrookrobotics.workshops.donottouch.util.Component
import java.awt.Color

class Leds : Component<Leds, Color>() {

    private val canifier = CANifier(60)

    override val fallbackController: Leds.() -> Color = { Color.BLACK }

    override fun output(value: Color) {
        canifier.setLEDOutput(value.red / 255.0, CANifier.LEDChannel.LEDChannelA)
        canifier.setLEDOutput(value.green / 255.0, CANifier.LEDChannel.LEDChannelB)
        canifier.setLEDOutput(value.blue / 255.0, CANifier.LEDChannel.LEDChannelC)
    }

}