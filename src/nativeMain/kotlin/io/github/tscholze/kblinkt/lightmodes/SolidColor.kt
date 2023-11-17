package io.github.tscholze.kblinkt.lightmodes

import io.github.tscholze.kblinkt.APA102


/**
 * All LEDs shine in bright red.
 */
fun APA102.solidRed() {
    leds.forEach {
        it.brightness = 1.0
        it.red = 255
        it.blue = 0
        it.green = 0
    }

    writeLedsValues()
}

/**
 * All LEDs shine in bright green.
 */
fun APA102.solidGreen() {
    leds.forEach {
        it.brightness = 1.0
        it.red = 0
        it.blue = 255
        it.green = 0
    }

    writeLedsValues()
}

/**
 * All LEDs shine in bright blue.
 */
fun APA102.solidBlue() {
    leds.forEach {
        it.brightness = 1.0
        it.red = 0
        it.blue = 0
        it.green = 255
    }

    writeLedsValues()
}