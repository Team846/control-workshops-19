package com.lynbrookrobotics.workshops

import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.RobotBase
import edu.wpi.first.wpilibj.Solenoid
import info.kunalsheth.units.generated.Percent

// BASE TEMPLATE
fun main(args: Array<String>) = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        val hook = Solenoid(2)
        val lift = Lift()
        val joystick = Joystick(0)

        var counter = 0

        HAL.observeUserProgramStarting()

        while (true) {

            hook.set(joystick.getRawButton(1))
            lift.set(
                    when (joystick.pov) {
                        0 -> 50.Percent
                        180 -> -50.Percent
                        else -> 0.Percent
                    }
            )

            counter = (counter + 1) % 10
            if (counter == 0) {
                print(lift.position)
            }

            m_ds.waitForData()
        }
    }
}