package com.lynbrookrobotics.workshops.donottouch

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import info.kunalsheth.units.generated.DutyCycle
import info.kunalsheth.units.generated.Each
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Length

class Lift {

    private val hardware = LiftHardware()

    /**
     * Get the current position (height) of the lift
     */
    val position: Length
        get() {
            // Converting native sensor units to physical height
            val nativeFeedbackUnits = LiftHardware.max.second - LiftHardware.min.second
            val perFeedbackQuantity = LiftHardware.max.first - LiftHardware.min.first

            return (perFeedbackQuantity / nativeFeedbackUnits * hardware.talon.getSelectedSensorPosition(0)) - LiftHardware.zeroOffset
        }

    /**
     * Set the current output of the motor from -1.0 to 1.0
     *
     * @param value of duty cycle from `-100.Percent` to `100.Percent`
     */
    fun set(value: DutyCycle) {
        hardware.talon.set(ControlMode.PercentOutput, value.Each)
    }

    /**
     * Log the lift's native and real position on smart dashboard
     */
    fun log() {
        SmartDashboard.putNumber("Position Real (Inches)", position.Inch)
        SmartDashboard.putNumber("Position Native", hardware.talon.getSelectedSensorPosition(0).toDouble())
    }

}