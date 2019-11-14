package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.ctre.phoenix.motorcontrol.ControlMode
import com.lynbrookrobotics.workshops.donottouch.util.Component
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import info.kunalsheth.units.generated.*

class Lift : Component<Lift, DutyCycle>() {

    private val hardware = LiftHardware()

    val position: Length
        get() {
            // Converting native sensor units to physical height
            val nativeFeedbackUnits = LiftHardware.max.second - LiftHardware.min.second
            val perFeedbackQuantity = LiftHardware.max.first - LiftHardware.min.first

            return (perFeedbackQuantity / nativeFeedbackUnits * hardware.talon.getSelectedSensorPosition(0)) - LiftHardware.zeroOffset
        }



    override val fallbackController: Lift.() -> DutyCycle = {
        0.Percent
    }

    override fun output(value: DutyCycle) {
        SmartDashboard.putNumber("Position Real (Inches)", position.Inch)
        SmartDashboard.putNumber("Position Native", hardware.talon.getSelectedSensorPosition(0).toDouble())
        hardware.talon.set(ControlMode.PercentOutput, value.Each)
    }

}