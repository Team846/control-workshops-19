package com.lynbrookrobotics.workshops

import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase

// BASE TEMPLATE
fun main(args: Array<String>) = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        // write initialization code here!

        HAL.observeUserProgramStarting()

        while (true) {

            // write control code here!

            m_ds.waitForData()
        }
    }
}