package com.lynbrookrobotics.workshops

import com.ctre.phoenix.motorcontrol.ControlFrame.Control_3_General
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod.Period_5Ms
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.milli

class Lift {

    companion object {
        private const val ESC_CAN_ID = 40
        private const val IDX = 0
        private const val INVERT = false
        private const val INVERT_SENSOR = false

        private const val TIMEOUT = 2500

        private val syncThreshold = 5.milli(Second)
        private val openloopRamp = 0.Second
        private val closedloopRamp = 0.Second
        private val peakOutputForward = 12.Volt
        private val peakOutputReverse = -peakOutputForward
        private val nominalOutputForward = 2.25.Volt
        private val nominalOutputReverse = -nominalOutputForward
        private val voltageCompSaturation = 12.Volt
        private val continuousCurrentLimit = 20.Ampere
        private val peakCurrentLimit = 35.Ampere
        private val peakCurrentDuration = 0.5.Second

        private val min = 0.Inch to 189
        private val max = 65.5.Inch to 792
        private val zeroOffset = 20.529.Inch
    }

    private val talon = TalonSRX(ESC_CAN_ID).apply {
        configFactoryDefault(TIMEOUT)
        setNeutralMode(NeutralMode.Brake)
        enableVoltageCompensation(true)
        enableCurrentLimit(true)
        setControlFramePeriod(Control_3_General, syncThreshold.milli(Second).toInt())
        inverted = INVERT
        setSensorPhase(INVERT_SENSOR)

        configNeutralDeadband(0.001, TIMEOUT)

        configForwardSoftLimitEnable(true, TIMEOUT)
        configReverseSoftLimitEnable(true, TIMEOUT)
        configForwardSoftLimitThreshold(max.second, TIMEOUT)
        configReverseSoftLimitThreshold(min.second, TIMEOUT)

        configOpenloopRamp(openloopRamp.Second, TIMEOUT)
        configClosedloopRamp(closedloopRamp.Second, TIMEOUT)
        configPeakOutputForward((peakOutputForward / voltageCompSaturation).Each, TIMEOUT)
        configPeakOutputReverse((peakOutputReverse / voltageCompSaturation).Each, TIMEOUT)
        configNominalOutputForward((nominalOutputForward / voltageCompSaturation).Each, TIMEOUT)
        configNominalOutputReverse((nominalOutputReverse / voltageCompSaturation).Each, TIMEOUT)
        configVoltageCompSaturation(voltageCompSaturation.Volt, TIMEOUT)
        configContinuousCurrentLimit(continuousCurrentLimit.Ampere.toInt(), TIMEOUT)
        configPeakCurrentLimit(peakCurrentLimit.Ampere.toInt(), TIMEOUT)
        configPeakCurrentDuration(peakCurrentDuration.milli(Second).toInt(), TIMEOUT)
        configSelectedFeedbackSensor(FeedbackDevice.Analog, IDX, TIMEOUT)
        configVelocityMeasurementPeriod(Period_5Ms, TIMEOUT)
        configVelocityMeasurementWindow(4, TIMEOUT)

        val configs = TalonSRXConfiguration().also { getAllConfigs(it, TIMEOUT) }
        check(configs.reverseSoftLimitThreshold == min.second && configs.forwardSoftLimitThreshold == max.second) {
            "Soft limits are not set correctly"
        }
    }

    val position: Length
        get() {
            val nativeFeedbackUnits = max.second - min.second
            val perFeedbackQuantity = max.first - min.first

            return (perFeedbackQuantity / nativeFeedbackUnits * talon.getSelectedSensorPosition(IDX)) - zeroOffset
        }

    fun set(value: DutyCycle) {
        talon.set(ControlMode.PercentOutput, value.Each)
    }

}