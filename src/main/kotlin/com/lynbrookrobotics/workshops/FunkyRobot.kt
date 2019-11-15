package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.subsystems.Drivetrain
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.RobotBase
import info.kunalsheth.units.generated.Percent

fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        val drivetrain = Drivetrain()
        val joystick = Joystick(0)

        HAL.observeUserProgramStarting()

        while (true) {
            m_ds.waitForData()
            drivetrain.output((joystick.y * 100.0).Percent)
        }
    }
}