package io.github.tscholze.kblinkt


/**
 * Validates if [this] represents a valid percentage value.
 * Rule: 0 >= x <= 1
 */
fun Double.isPercentValue(): Boolean {
    return !(this < 0 || this > 1)
}

/**
 * Validates if [this] represents a valid hex value.
 * Rule: 0 >= x <= 255
 */
fun Int.isHexValue(): Boolean {
    return !(this < 0 || this > 255)
}