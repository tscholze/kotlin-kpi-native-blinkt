package io.github.tscholze.kblinkt.lightmodes

import io.github.tscholze.kblinkt.APA102
import io.ktgp.util.sleep

/**
 * Cycles all colors given times with a
 * second between each color switch.
 *
 * @param times Number of cycles. Default value: 10
 */
fun APA102.cycle(times: Int = 10) {
    repeat(times) {
        solidRed()
        sleep(1_000)
        solidGreen()
        sleep(1_000)
        solidBlue()
        sleep(1_000)
    }
}