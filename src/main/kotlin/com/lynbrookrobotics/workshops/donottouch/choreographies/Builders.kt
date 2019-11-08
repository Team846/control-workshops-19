package com.lynbrookrobotics.workshops.donottouch.choreographies

import com.lynbrookrobotics.workshops.donottouch.timing.EventLoop
import kotlinx.coroutines.*
import kotlin.coroutines.resume

/**
 * Runs a block whenever the predicate is met.
 *
 * Even if the predicate turns false while the block is running, the block will not be cancelled.
 *
 * @see waitUntil
 *
 * @param predicate function to check if the block should be run.
 * @param block function to run.
 */
suspend fun whenever(predicate: () -> Boolean, block: Block) = coroutineScope {
    var cont: CancellableContinuation<Unit>? = null


    val runOnTick = EventLoop.runOnTick {
        if (predicate() && cont?.isActive == true) {
            try {
                cont?.resume(Unit)
            } catch (c: CancellationException) {
                throw c
            } catch (e: IllegalStateException) {
            }
        }
    }

    try {
        while (isActive) {
            suspendCancellableCoroutine<Unit> { cont = it }
            block()
        }
    } finally {
        runOnTick.cancel()
    }
}

/**
 * Creates a new coroutine that runs `runWhile`s in a `whenever`.
 *
 * Whenever a predicate is true, its block will be run.
 * However, the block will be cancelled once its predicate returns false.
 *
 * @see runWhile
 * @see whenever
 *
 * @param blocks list of pairs of a predicate and a block.
 */
suspend fun runWhenever(vararg blocks: Pair<() -> Boolean, Block>) = supervisorScope {
    blocks.forEach { (p, b) ->
        launch {
            whenever(p) {
                runWhile(p, b)
            }
        }
    }
}

/**
 * Creates a new coroutine that launches blocks in a `whenever` block.
 *
 * Whenever a predicate is true, its block will be run.
 * The block will not be cancelled even if its predicate returns false while the block is running.
 *
 * @see whenever
 *
 * @param blocks List of pairs of a predicate and a function
 */
fun CoroutineScope.launchWhenever(vararg blocks: Pair<() -> Boolean, Block>) = launch {
    blocks.forEach { (p, b) ->
        whenever(p) {
            try {
                b()
            } catch (e: Exception) {
            }
        }
    }
}

fun CoroutineScope.launch(vararg blocks: Block) {
    blocks.forEach { launch { it() } }
}