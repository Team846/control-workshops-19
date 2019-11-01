package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.Lift
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.Solenoid
import info.kunalsheth.units.generated.DutyCycle
import info.kunalsheth.units.generated.Percent

// BASE TEMPLATE
fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        // write initialization code here!



        HAL.observeUserProgramStarting()

        while (true) {
            // write control code here!



            m_ds.waitForData(0.5)
        }
    }

}