package com.lynbrookrobotics.workshops.donottouch.subsystems.lift

import com.ctre.phoenix.motorcontrol.ControlFrame
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration
import com.lynbrookrobotics.workshops.donottouch.subsystems.SubsystemHardware
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.milli

class LiftHardware : SubsystemHardware<LiftHardware, LiftComponent>() {

    override val syncThreshold = 5.milli(Second)

    companion object {
        private const val ESC_CAN_ID = 40

        private const val INVERT = false
        private const val INVERT_SENSOR = false
        private const val TIMEOUT = 2500


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

        val min = 9.9.Inch to 280
        val max = 54.4.Inch to 690
        val zeroOffset = 20.529.Inch
    }

    val talon = TalonSRX(ESC_CAN_ID).apply {
        configFactoryDefault(TIMEOUT)
        setNeutralMode(NeutralMode.Brake)
        enableVoltageCompensation(true)
        enableCurrentLimit(true)
        setControlFramePeriod(ControlFrame.Control_3_General, syncThreshold.milli(Second).toInt())
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
        configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, TIMEOUT)
        configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_5Ms, TIMEOUT)
        configVelocityMeasurementWindow(4, TIMEOUT)

        val configs = TalonSRXConfiguration().also { getAllConfigs(it, TIMEOUT) }
        check(configs.reverseSoftLimitThreshold == min.second && configs.forwardSoftLimitThreshold == max.second) {
            "Soft limits are not set correctly"
        }
    }

}