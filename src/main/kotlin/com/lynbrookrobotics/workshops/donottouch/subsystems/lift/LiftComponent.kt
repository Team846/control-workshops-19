package com.lynbrookrobotics.workshops.donottouch.subsystems.lift

import com.ctre.phoenix.motorcontrol.ControlMode
import com.lynbrookrobotics.workshops.donottouch.subsystems.Component
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import info.kunalsheth.units.generated.*

class LiftComponent(hardware: LiftHardware) : Component<LiftComponent, LiftHardware, DutyCycle>(hardware) {

    val position: Length
        get() {
            // Converting native sensor units to physical height
            val nativeFeedbackUnits = LiftHardware.max.second - LiftHardware.min.second
            val perFeedbackQuantity = LiftHardware.max.first - LiftHardware.min.first

            return (perFeedbackQuantity / nativeFeedbackUnits * hardware.talon.getSelectedSensorPosition(0)) - LiftHardware.zeroOffset
        }

    override val fallbackController: LiftComponent.() -> DutyCycle = {
        0.Percent
    }

    override fun LiftHardware.output(value: DutyCycle) {
        SmartDashboard.putNumber("Position Real (Inches)", position.Inch)
        SmartDashboard.putNumber("Position Native", hardware.talon.getSelectedSensorPosition(0).toDouble())
        hardware.talon.set(ControlMode.PercentOutput, value.Each)
    }

}