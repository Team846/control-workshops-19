package com.lynbrookrobotics.workshops.donottouch.subsystems.hook

import com.lynbrookrobotics.workshops.donottouch.subsystems.SubsystemHardware
import edu.wpi.first.wpilibj.Solenoid
import info.kunalsheth.units.generated.Second
import info.kunalsheth.units.math.milli

class HookHardware : SubsystemHardware<HookHardware, HookComponent>() {

    override val syncThreshold = 20.milli(Second)

    val solenoid = Solenoid(2)

}