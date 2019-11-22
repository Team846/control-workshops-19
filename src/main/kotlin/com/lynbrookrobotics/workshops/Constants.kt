package com.lynbrookrobotics.workshops

import com.ctre.phoenix.motorcontrol.ControlFrame
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod
import com.ctre.phoenix.motorcontrol.can.TalonSRX

const val configTimeout = 1000
const val pidIdx = 0

fun configEsc(esc: TalonSRX) {
    esc.setNeutralMode(NeutralMode.Coast)
    esc.configOpenloopRamp(0.0, configTimeout)
    esc.configClosedloopRamp(0.0, configTimeout)

    esc.configPeakOutputReverse(-1.0, configTimeout)
    esc.configNominalOutputReverse(0.0, configTimeout)
    esc.configNominalOutputForward(0.0, configTimeout)
    esc.configPeakOutputForward(1.0, configTimeout)
    esc.configNeutralDeadband(0.001, configTimeout)

    esc.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, configTimeout)

    esc.configVelocityMeasurementPeriod(VelocityMeasPeriod.Period_5Ms, configTimeout)
    esc.configVelocityMeasurementWindow(4, configTimeout)

    esc.setControlFramePeriod(ControlFrame.Control_3_General, 5)

    esc.configVoltageCompSaturation(12.0, configTimeout)
    esc.enableVoltageCompensation(true)

    esc.enableCurrentLimit(false)
}