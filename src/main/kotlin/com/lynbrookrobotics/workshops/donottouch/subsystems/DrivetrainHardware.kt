package com.lynbrookrobotics.workshops.donottouch.subsystems

import com.ctre.phoenix.motorcontrol.ControlFrame
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.milli

class DrivetrainHardware {

    companion object {
        private const val LEFT_ESC_ID = 10

        private const val INVERT = false
        private const val INVERT_SENSOR = false
        private const val TIMEOUT = 2500

        private val syncThreshold = 4.milli(Second)

        private val openloopRamp = 0.Second
        private val closedloopRamp = 0.Second
        private val peakOutputForward = 12.Volt
        private val peakOutputReverse = -peakOutputForward
        private val nominalOutputForward = 0.5.Volt
        private val nominalOutputReverse = -nominalOutputForward
        private val voltageCompSaturation = 12.Volt
        private val continuousCurrentLimit = 25.Ampere
        private val peakCurrentLimit = 35.Ampere
        private val peakCurrentDuration = 1.Second
    }

    val talon = TalonSRX(LEFT_ESC_ID).apply {
        configFactoryDefault(TIMEOUT)
        setNeutralMode(NeutralMode.Brake)
        enableVoltageCompensation(true)
        enableCurrentLimit(true)
        setControlFramePeriod(ControlFrame.Control_3_General, syncThreshold.milli(Second).toInt())
        inverted = INVERT
        selectedSensorPosition = 1
        setSensorPhase(INVERT_SENSOR)

        configNeutralDeadband(0.001, TIMEOUT)

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
        configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT)
        configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_5Ms, TIMEOUT)
        configVelocityMeasurementWindow(4, TIMEOUT)
    }
}