package com.lynbrookrobotics.workshops

import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.Solenoid
import info.kunalsheth.units.generated.Percent

// BASE TEMPLATE
fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        // write initialization code here!

        val hook = Solenoid(2)
        val lift = Lift()
        val joystick = Joystick(0)

        HAL.observeUserProgramStarting()

        while (true) {
            // write control code here!

            hook.set(joystick.getRawButton(0));
            lift.set(
                    when (joystick.pov) {
                        0 -> 20.Percent
                        180 -> -20.Percent
                        else -> 0.Percent
                    }
            )

            lift.log()
            m_ds.waitForData()
        }
    }
}