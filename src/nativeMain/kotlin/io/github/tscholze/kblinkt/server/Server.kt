package io.github.tscholze.kblinkt.server

import io.github.tscholze.kblinkt.apa102.Command
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.MutableSharedFlow
import platform.posix.exit

/**
 * Starts and runs the embedded server.
 *
 * @param actions: Flow in which requested action via API should be emitted.
 */
fun runServer(actions: MutableSharedFlow<Command>): ApplicationEngine {
    return embeddedServer(CIO, port = 8080) {
        routing {
            // Listen to GET requests on root
            get("/") {
                call.respondText(contentType = ContentType.Text.Html, text = template)
            }

            // Listen to POST requests on /on
            post("on") {
                actions.emit(Command.TurnOn)
                call.respond(HttpStatusCode.OK)
            }

            // Listen to POST requests on /off
            post("off") {
                actions.emit(Command.TurnOff)
                call.respond(HttpStatusCode.OK)
            }

            post("lightmode") {
                val id = call.receiveText()
                actions.emit(Command.LightMode(id))
                call.respond(HttpStatusCode.OK)
            }

            post("morse") {
                val text = call.receiveText()
                actions.emit(Command.Morse(text))
                call.respond(HttpStatusCode.OK)
            }

            post("shutdown") {
                actions.emit(Command.Shutdown)
                call.respond(HttpStatusCode.OK)
                exit(0)
            }
        }
    }.start()
}

/**
 * In Kotlin Native, you cannot load Ktor content from
 * `resources`, to have a simple template string was the
 * best of the worse.
 */
private val template = """
<html lang="en">
<head>
<title>KPi - Blinkt!</title>

<script>

let xhr = new XMLHttpRequest();

function turnOn() {
xhr.open("POST", "/on");
xhr.send()
}

function turnOff() {
xhr.open("POST", "/off");
xhr.send()
}

function turnLightMode(id) {
xhr.open("POST", "/lightmode");
xhr.send(id)
}

function morseWord() {
const text = document.getElementById("morse_text").value.trim();
xhr.open("POST", "/morse");
xhr.send(text)
}

function shutdown() {
xhr.open("POST", "/shutdown");
xhr.send()
}
</script>
</head>

<body>
<h1>Kotlin Native Blinkt! controller</h1>
<table>
<tr>
<td>
<fieldset>
<legend>Light switch</legend>
<button onclick="turnOn()">On</button>
<button onclick="turnOff()">Off</button>
</fieldset>
</td>
<td>
<fieldset>
<legend>Light modes</legend>
<button onclick="turnLightMode('red')">Red</button>
<button onclick="turnLightMode('green')">Green</button>
<button onclick="turnLightMode('blue')">Blue</button>
<button onclick="turnLightMode('rainbow')">Rainbow</button>
<button onclick="turnLightMode('cycle')">Cycle</button>
</fieldset>
</td>
</tr>
<tr>
<td>
<fieldset>
<legend>Morse word</legend>
<label for="morse_text"></label><input id="morse_text" type="text"/>
<button onclick="morseWord()">Morse it</button>
</fieldset>
</td>
<td>
<fieldset>
<legend>System</legend>
<button onclick="shutdown()">Shutdown</button>
</fieldset>
</td>
</tr>
</table>
<p>Try it, maybe it will explode.</p>
</body>
</html>
""".trimIndent()
