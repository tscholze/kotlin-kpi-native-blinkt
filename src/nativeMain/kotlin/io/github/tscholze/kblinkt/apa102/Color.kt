package io.github.tscholze.kblinkt.apa102

import io.github.tscholze.kblinkt.utils.isHexValue
import io.github.tscholze.kblinkt.utils.isPercentValue
import io.ktor.utils.io.errors.*

/**
 * Describes a set of predefined colors
 * that could be used to control LEDs.
 *
 * Valid values:
 *  - R,G,B values must be in range from 0.255
 *  - Brightness must be in range of 0..1
 *
 * @param red Red part of the color. Default value: 0
 * @param green Green part of the color. Default value: 0
 * @param blue Blue part of the color. Default value: 0
 * @param brightness How intense the color should be. Default value 1.0
 *
 * @throws [Exception] if given values are not valid.
 */
sealed class Color(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
    val brightness: Double = 1.0
) {
    // MARK: - Predefined values -

    /** White color, rgb are set to 255 */
    data object White : Color(255, 255, 255)

    /** Red color, r = 255, gb = 0 */
    data object Red : Color(red = 255)

    /** Green color, g = 255, rb = 0 */
    data object Green : Color(green = 255)

    /** Blue color, b = 255, rg = 0 */
    data object Blue : Color(blue = 255)

    /** Black (off) color, rgb = 0 */
    data object Black : Color(brightness = 0.0)

    // MARK: - Init -

    /**
     * Initializer which valides the given rgb and brightness values.
     * If validation fails, an [Exception] will be thrown.
     *
     * @throws [Exception] if given values are not valid.
     */
    init {
        if (!red.isHexValue() || !green.isHexValue() || !blue.isHexValue() || !brightness.isPercentValue()) {
            print("Invalid color definition. Reset to black (off).")
            throw Exception("Invalid color values.")
        }
    }
}