import io.ktgp.gpio.Gpio
import io.ktgp.gpio.Output
import io.ktgp.gpio.PinState
import io.ktgp.util.sleep

class APA102(gpio: Gpio) {
    // MARK: - Private properties -

    /** List of attached LEDs **/
    private var leds = mutableListOf<APA102LED>()

    /** GPIO pin for data connections */
    private val dataPin: Output

    /** GPIO pin to access the clock */
    private val clockPin: Output

    // MARK: - Init -

    init {
        // Create list of LEDs
        repeat(Companion.NUMBER_OF_LEDS) {
            leds.add(APA102LED())
        }

        // Setup GPIO pins
        dataPin = gpio.output(Companion.GPIO_PIN_DATA)
        clockPin = gpio.output(Companion.GPIO_PIN_CLOCK)

        // Turn it on!
        turnAllOn()
    }

    /**
     * Closes and cleans the connections and pins
     * Must be called if program has been finished.
     */
    fun close() {
        println("Close")
        turnAllOff()
        writeLedsValues()
        dataPin.close()
        clockPin.close()
    }

    // MARK: - Private helper -

    fun cycle() {
        repeat(20) {
            sleep(1_000)
            turnAllOff()

            leds.forEach {
                it.brightness = 1.0
                it.red = 255
                it.blue = 0
                it.green = 0
            }
            writeLedsValues()
            sleep(1_000)

            leds.forEach {
                it.brightness = 1.0
                it.red = 0
                it.blue = 255
                it.green = 0
            }
            writeLedsValues()
            sleep(1_000)

            leds.forEach {
                it.brightness = 1.0
                it.red = 0
                it.blue = 0
                it.green = 255
            }
            writeLedsValues()
        }
    }

    /**
     * Turn all LEDs on.
     */
    private fun turnAllOn() {
        println("turnAllOn")
        leds.forEach { it.turnOn() }
        writeLedsValues()
    }

    /**
     * Turn all LEDs off.
     */
    private fun turnAllOff() {
        leds.forEach { it.turnOff() }
    }

    /**
     * Updates the clock to locked
     * or unlocked state.
     */
    private fun setClockState(locked: Boolean) {
        val numberOfPulses =
            if (locked) Companion.NUMBER_OF_CLOCK_LOCK_PULSES else Companion.NUMBER_OF_CLOCK_UNLOCK_PULSES

        // Prepare data pin
        dataPin.setState(PinState.LOW)

        // Pulse clock pin
        repeat(numberOfPulses) {
            clockPin.setState(PinState.HIGH)
            clockPin.setState(PinState.LOW)
        }
    }

    /**
     * Writes stored LED values to the HAT.
     * Required for applying stored LED values.
     */
    private fun writeLedsValues() {
        setClockState(true)
        for (led in leds) {
            writeLED(led)
        }
        setClockState(false)
    }

    /**
     * Writes LED values to the HAT
     * @param led which shall be updated.
     */
    private fun writeLED(led: APA102LED) {
        val sendBright = (31.0 * led.brightness).toInt() and 31
        writeByte((224 or sendBright).toByte())
        writeByte(led.blue.toByte())
        writeByte(led.green.toByte())
        writeByte(led.red.toByte())
    }

    /**
     * Low level writing of bytes to hardware led
     *
     * @param input Data to write.
     */
    private fun writeByte(input: Byte) {
        var value: Int
        var modded = input.toInt()

        for (count in 0..7) {
            value = modded and 128
            dataPin.setState(if (value == 128) PinState.HIGH else PinState.LOW)
            clockPin.setState(PinState.HIGH)
            modded = (modded shl 1) % 256
            clockPin.setState(PinState.LOW)
        }
    }

    // MARK: - Companion object -

    companion object {
        /** Data BCM GPIO pin number.*/
        private val GPIO_PIN_DATA = 23;

        /** Clock BCM GPIO pin numbers.*/
        private val GPIO_PIN_CLOCK = 24;

        /**  Number of available leds on the APA102 (Blinkt).*/
        private val NUMBER_OF_LEDS = 8;

        /** Number of pulses that is required to lock the clock.*/
        private val NUMBER_OF_CLOCK_LOCK_PULSES = 36;

        /** Number of pulses that is required to release the clock.*/
        private val NUMBER_OF_CLOCK_UNLOCK_PULSES = 32;
    }
}

/**
 * Represents a LED that's controlled
 * via an APA102 chip.
 */
class APA102LED(
    var red: Int = 1,
    var green: Int = 1,
    var blue: Int = 1,
    var brightness: Double = 1.0
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