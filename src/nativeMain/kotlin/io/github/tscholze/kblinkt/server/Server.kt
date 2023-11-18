package io.github.tscholze.kblinkt.server

import io.github.tscholze.kblinkt.apa102.Action
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Starts and runs the embedded server.
 *
 * @param actions: Flow in which requested action via API should be emitted.
 */
fun runServer(actions: MutableSharedFlow<Action>): ApplicationEngine {
    return embeddedServer(CIO, port = 8080) {
        routing {
            // Listen to GET requests on root
            get("/") {
                call.respondText("Hi from KBlinkt")
            }

            // Listen to POST requests on /on
            post("on") {
                actions.emit(Action.TurnOn)
            }

            // Listen to POST requests on /off
            post("off") {
                actions.emit(Action.TurnOff)
            }
        }
    }
}


