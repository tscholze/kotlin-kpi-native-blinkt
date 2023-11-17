package io.github.tscholze.kblinkt.lightmodes

import io.github.tscholze.kblinkt.APA102

/**
 * Sets colors to kinda rainbow gradient.
 */
fun APA102.rainbow() {
    setRgbForLed(0, 255, 255, 255)

    setRgbForLed(1, 255, 0, 0)
    setRgbForLed(2, 255, 0, 0)

    setRgbForLed(3, 0, 255, 0)
    setRgbForLed(4, 0, 255, 0)

    setRgbForLed(5, 0, 0, 255)
    setRgbForLed(7, 0, 0, 255)

    setRgbForLed(7, 255, 255, 255)

}