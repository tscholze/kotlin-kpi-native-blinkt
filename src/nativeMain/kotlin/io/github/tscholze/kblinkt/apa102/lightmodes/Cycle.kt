package io.github.tscholze.kblinkt.lightmodes

import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.apa102.Color
import io.ktgp.util.sleep

/**
 * Cycles all colors given times with a
 * second between each color switch.
 *
 * @param times Number of cycles. Default value: 10
 */
fun APA102.cycle(times: Int = 10) {
    repeat(times) {
        setColor(Color.Red)
        sleep(1_000)
        setColor(Color.Green)
        sleep(1_000)
        setColor(Color.Blue)
        sleep(1_000)
    }
}