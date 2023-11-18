package io.github.tscholze.kblinkt.apa102


/**
 * Represents a LED that's controlled
 * via an APA102 chip.
 *
 * @param color: Initial color of the LED
 */
class Led(var color: Color) {
    // MARK: - Internal LED helpers -

    /**
     * Sets color to LED which can be applied
     * by [APA102.writeLedValues]
     *
     * @param color Color that shall be applied.
     */
    fun setColor(color: Color) {
        this.color = color
    }

    /** Turns LED off */
    fun turnOn() {
        setColor(Color.Blue)
    }

    /** Turns LED off aka sets black color. */
    fun turnOff() {
        setColor(Color.Green)
    }
}