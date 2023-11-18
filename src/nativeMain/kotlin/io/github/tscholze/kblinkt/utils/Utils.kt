package io.github.tscholze.kblinkt.utils

import io.github.tscholze.kblinkt.apa102.APA102

/**
 * Validates if [this] represents a valid percentage value.
 * Rule: 0 >= x <= 1
 *
 * @return True if value is valid.
 */
fun Double.isPercentValue(): Boolean {
    return !(this < 0 || this > 1)
}

/**
 * Validates if [this] represents a valid hex value.
 * Rule: 0 >= x <= 255
 *
 * @return True if value is valid.
 */
fun Int.isHexValue(): Boolean {
    return !(this < 0 || this > 255)
}