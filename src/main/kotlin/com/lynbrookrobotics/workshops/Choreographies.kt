package com.lynbrookrobotics.workshops

import com.lynbrookrobotics.workshops.donottouch.choreographies.choreography
import com.lynbrookrobotics.workshops.donottouch.choreographies.delay
import com.lynbrookrobotics.workshops.donottouch.subsystems.Subsystems
import info.kunalsheth.units.generated.Inch
import info.kunalsheth.units.generated.Second
import kotlinx.coroutines.launch

suspend fun Subsystems.example() = choreography("Example") {

    val hello = "Hello"

    onStart {
        print(hello)
    }

    onEnd {
        println(" world")
    }

}