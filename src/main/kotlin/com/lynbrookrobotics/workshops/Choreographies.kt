package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.choreographies.choreography
import com.lynbrookrobotics.workshops.donottouch.choreographies.delay
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Second
import kotlinx.coroutines.launch

// CHALLENGE #4
private val panelCollectHeight = 12.Inch
private val panelCollectStrokeHeight = 15.Inch

suspend fun Subsystems.panelCollect() = choreography("Panel Collect") {

    // Initialization code here (run only once) e.g. variables

    onStart {
        // Called when the button is pressed
    }

    onEnd {
        // Called once the button is released
    }

}