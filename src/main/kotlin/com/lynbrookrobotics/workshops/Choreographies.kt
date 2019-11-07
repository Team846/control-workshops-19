package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.Subsystems
import com.lynbrookrobotics.workshops.donottouch.choreographies.*
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Second
import info.kunalsheth.units.math.abs
import kotlinx.coroutines.launch

suspend fun Subsystems.exampleChoreography() = choreography("Example!") {

    onStart {
        println("Example choreo start")
    }

    onEnd {
        println("Example choreo end!")
    }

}

suspend fun Subsystems.panelCollect() = choreography("Panel Collect") {

    val panelCollectHeight = 3.23.Inch
    val panelCollectStrokeHeight = 8.77.Inch

    onStart {
        launch(
                { hook.set(true) },
                { lift.set(panelCollectHeight) }
        )
        freeze()
    }

    onEnd {
        withTimeout(1.Second) {
            launch { lift.set(panelCollectStrokeHeight) }
            waitUntil {
                abs(lift.position - panelCollectStrokeHeight) < 0.5.Inch
            }
        }
        launch { slider.set(true) }
        delay(0.2.Second)
        launch { hook.set(false) }
        delay(0.2.Second)
        launch { slider.set(false) }
    }

}