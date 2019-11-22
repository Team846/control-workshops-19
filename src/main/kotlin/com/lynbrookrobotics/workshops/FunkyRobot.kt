package com.lynbrookrobotics.workshops

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.RobotController
import edu.wpi.first.wpilibj.RobotState
import edu.wpi.first.wpilibj.Timer
import info.kunalsheth.units.generated.*
import info.kunalsheth.units.math.milli

// WEEK SIX
fun main(args: Array<String>) = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    companion object { // think of this as Java's static space
        val topSpeed = 15840.Each / 100.milli(Second)
    }

    override fun startCompetition() { // think of this as your main method

        // write initialization code here!
        val esc = TalonSRX(12).also(::configEsc) // init and config ESC

        HAL.observeUserProgramStarting() // tell the Driver Station that we are done initializing
        while (true) {
            val time = Timer.getFPGATimestamp().Second
            val battery = RobotController.getBatteryVoltage().Volt
            val position = esc.getSelectedSensorPosition(pidIdx).Each // encoder ticks
            val speed = esc.getSelectedSensorVelocity(pidIdx).Each / 100.milli(Second) // # of ticks per 100ms

            var output = 0.Percent

            // write control code here!

            esc.set(ControlMode.PercentOutput, output.Each) // set ESC duty cycle
            log(time, battery, position, speed, output) // write to log file
            if (RobotState.isDisabled()) flushLog() // flush the log file when disabled
            m_ds.waitForData() // get new data from the Driver Station
        }
    }
}