package io.github.tscholze.kblinkt.apa102

/**
 * Defines all available action that the
 * [APA102] can be requested for.
 */
sealed class Command {
    /** Turns all LEDs on with a bright white color on */
    data object TurnOn : Command()

    /** Turns all LEDs off which means setting a color of black */
    data object TurnOff : Command()

    /** A custom lightmode has been requested */
    data class LightMode(val id: String) : Command()

    /** A morsing of given text has been requested */
    data class Morse(val text: String) : Command()

    /** Command to shutdown server and gpio and exit the program */
    data object Shutdown : Command()
}