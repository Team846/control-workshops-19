package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import info.kunalsheth.units.generated.DutyCycle
import info.kunalsheth.units.generated.Each

class Drivetrain {

    private val hardware = DrivetrainHardware()

    fun output(value: DutyCycle) {
        hardware.talon.set(ControlMode.PercentOutput, value.Each)
    }

}