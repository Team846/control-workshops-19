package com.lynbrookrobotics.workshops.donottouch.subsystems.slider

import com.lynbrookrobotics.workshops.donottouch.subsystems.SubsystemHardware
import edu.wpi.first.wpilibj.Solenoid
import info.kunalsheth.units.generated.Second
import info.kunalsheth.units.math.milli

class SliderHardware : SubsystemHardware<SliderHardware, SliderComponent>() {

    override val syncThreshold = 20.milli(Second)

    val solenoid = Solenoid(1)

}