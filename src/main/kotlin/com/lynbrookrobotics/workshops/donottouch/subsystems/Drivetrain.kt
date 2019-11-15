package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.milli

class Drivetrain {

    private val hardware = DrivetrainHardware()

    val feedback get() = hardware.talon.getSelectedSensorVelocity(0).Each / 100.milli(Second)

    fun output(value: DutyCycle) {
        SmartDashboard.putNumber("Drivetrain Feedback", feedback.Hertz)
        hardware.talon.set(ControlMode.PercentOutput, -value.Each)
    }

}