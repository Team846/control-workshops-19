package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.choreographies.block
import com.lynbrookrobotics.workshops.donottouch.choreographies.freeze
import com.lynbrookrobotics.workshops.donottouch.choreographies.runWhenever
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import edu.wpi.first.hal.HAL
import edu.wpi.first.wpilibj.RobotBase
import info.kunalsheth.units.generated.Percent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun main() = RobotBase.startRobot(::FunkyRobot)

class FunkyRobot : RobotBase() {

    override fun startCompetition() {

        val subsystems = Subsystems()

        GlobalScope.launch {
            runWhenever(
                    { subsystems.joystick.getRawButton(1) } to block {
                        subsystems.panelCollect()
                    },
                    { subsystems.joystick.pov == 0 } to block {
                        subsystems.lift.set(80.Percent)
                    },
                    { subsystems.joystick.pov == 180 } to block {
                        subsystems.lift.set(-50.Percent)
                    },
                    { subsystems.joystick.pov == 0 } to block {
                        subsystems.lift.set(-0.Percent)
                    }
            )
            freeze()
        }

        HAL.observeUserProgramStarting()

        while (true) {
            m_ds.waitForData()
            EventLoop.tick()
        }
    }
}