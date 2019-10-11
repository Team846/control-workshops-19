package com.lynbrookrobotics.workshops

import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.Solenoid
import edu.wpi.first.wpilibj.Joystick

fun main(args: Array<String>) = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        // write initialization code here!

        val pivot = /* Make a solenoid! */
        val joystick = /* Make a joystick */

        HAL.observeUserProgramStarting()

        while (true) {

            // write control code here!

            if (/* Check if the joystick trigger is pressed */) {
                /* Move the pivot down! */
            }


            m_ds.waitForData()
        }
    }
}