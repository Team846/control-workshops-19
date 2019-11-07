package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import com.lynbrookrobotics.workshops.donottouch.choreographies.block
import com.lynbrookrobotics.workshops.donottouch.choreographies.runWhenever
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase
import kotlinx.coroutines.GlobalScope

fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        val subsystems = Subsystems()

        HAL.observeUserProgramStarting()

        GlobalScope.runWhenever(
                { subsystems.joystick.getRawButton(1) } to block {
                    subsystems.panelCollect()
                }
        )

        while (true) {
            EventLoop.tick()
            m_ds.waitForData()
        }
    }
}