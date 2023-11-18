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
}