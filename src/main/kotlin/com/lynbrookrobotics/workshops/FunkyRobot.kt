package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.choreographies.block
import com.lynbrookrobotics.workshops.donottouch.choreographies.freeze
import com.lynbrookrobotics.workshops.donottouch.choreographies.runWhenever
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        val subsystems = Subsystems()

        // Initialization code

        // CHALLENGE #1/2/3
//        GlobalScope.launch { subsystems.lift.set(30.Inch) }

        // CHALLENGE #4
//        GlobalScope.launch {
//            runWhenever(
//                    { subsystems.joystick.getRawButton(1) } to block {
//                        subsystems.panelCollect()
//                    }
//            )
//            freeze()
//        }

        HAL.observeUserProgramStarting()

        while (true) {
            m_ds.waitForData()
            EventLoop.tick()
        }
    }
}