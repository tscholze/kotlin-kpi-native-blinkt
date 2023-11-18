package io.github.tscholze.kblinkt.lightmodes

import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.apa102.Color

/**
 * Sets colors to kinda rainbow gradient.
 *
 * Colors:
 * LED 0 = white
 * LED 1-2 = red
 * LED 3-4 = green
 * LED 5-6 = blue
 * LED 7 = white
 */
fun APA102.rainbow() {
    // First 0. LED = white
    setColor(Color.White, 0..0)

    // 1. & 2. LED = red
    setColor(Color.Red, 1..2)

    // 3. & 4. LED = green
    setColor(Color.Green, 3..4)

    // 5. & 6. LED = blue
    setColor(Color.Blue, 5..6)

    // Last 7. LED = white
    setColor(Color.White, 7..7)

}