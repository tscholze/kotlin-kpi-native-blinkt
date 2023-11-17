package io.github.tscholze.kblinkt.apa102

import io.github.tscholze.kblinkt.utils.isHexValue
import io.github.tscholze.kblinkt.utils.isPercentValue


/**
 * Represents a LED that's controlled
 * via an APA102 chip.
 *
 * @param red: Initial red part value. Default value: 0
 * @param green: Initial green part value. Default value: 0
 * @param blue: Initial blue part value. Default value: 0
 * @param brightness: Initial brightness part value. Default value: 0.0
 */
class Led(
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0,
    var brightness: Double = 0.0
) {
    // MARK: - Internal LED helpers -

    /**
     * Sets the brightness.
     * Accepted values: 0 >= x <= 1
     *
     * @param value Brightness that shall be set.
     */
    fun setBrightness(value: Double) {
        if (value.isPercentValue()) {
            brightness = value
            return
        } else {
            println("Error")
        }
    }

    /**
     * Sets the red part of the emitted light.
     * Accepted values: 0 >= x <= 255
     *
     * @param value New value of red.
     */
    fun setRed(value: Int) {
        if (value.isHexValue()) {
            red = value
        } else {
            println("Error")
        }
    }

    /**
     * Sets the greem part of the emitted light.
     * Accepted values: 0 >= x <= 255
     *
     * @param value New value of green.
     */
    fun setGreen(value: Int) {
        if (value.isHexValue()) {
            green = value
        } else {
            println("Error")
        }
    }

    /**
     * Sets the blue part of the emitted light.
     * Accepted values: 0 >= x <= 255
     *
     * @param value New value of blue.
     */
    fun setBlue(value: Int) {
        if (value.isHexValue()) {
            blue = value
        } else {
            println("Error")
        }
    }

    /**
     * Sets all colors of the LED.
     *
     * @param red: New red value
     * @param green: New green value
     * @param blue: New blue value
     * @param brightness: New brightness value
     */
    fun setRgb(red: Int, green: Int, blue: Int, brightness: Double = 1.0) {
        setBrightness(brightness)

        setRed(red)
        setGreen(green)
        setBlue(blue)
    }

    /**
     * Turns LED off
     */
    fun turnOn() {
        setRgb(
            255,
            255,
            255,
            1.0
        )
    }

    /**
     * Turns LED on with full bright white color.
     */
    fun turnOff() {
        setRgb(
            0,
            0,
            0,
            0.0
        )
    }
}