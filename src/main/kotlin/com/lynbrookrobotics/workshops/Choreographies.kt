package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.choreographies.choreography
import com.lynbrookrobotics.workshops.donottouch.choreographies.delay
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Second
import kotlinx.coroutines.launch

suspend fun Subsystems.panelCollect() = choreography("Panel Collect") {

    val panelCollectHeight = 24.Inch
    val panelCollectStrokeHeight = 30.Inch

    onStart {
        launch { hook.set(true) }
        lift.set(panelCollectHeight, 0.5.Inch)
    }

    onEnd {
        launch { lift.set(panelCollectStrokeHeight, 0.5.Inch) }
        delay(0.2.Second)
        launch { slider.set(true) }
        delay(0.2.Second)
        launch { hook.set(false) }
        delay(0.2.Second)
        launch { slider.set(false) }
    }
}