package io.github.tscholze.kblinkt.apa102

import io.ktgp.gpio.Gpio
import io.ktgp.gpio.Output
import io.ktgp.gpio.PinState

/**
 * Represents an APA102 chip manager.
 * It allows the user to controll the embedded LEDs
 *
 * This controller is a port if my HomeBear.Blinkt Windows 10 IoT Core app.
 * > https://github.com/tscholze/dotnet-iot-homebear-blinkt/tree/master/HomeBear.Blinkt/Controller
 *
 * After usage the method [close] shall be called to clean up GPIO usage.
 *
 * @param gpio GPIO Controller to access pins.
 */
class APA102(gpio: Gpio) {
    // MARK: - Private properties -

    /** List of attached LEDs **/
    private var leds = mutableListOf<Led>()

    /** GPIO pin for data connections */
    private val dataPin: Output

    /** GPIO pin to access the clock */
    private val clockPin: Output

    // MARK: - Init -

    /**
     * Initializer which sets up pins and
     * LED objcets that shall be controlled
     * using the controller.
     */
    init {
        // Create list of LEDs
        repeat(NUMBER_OF_LEDS) {
            leds.add(Led(Color.Black))
        }

        // Setup GPIO pins
        dataPin = gpio.output(GPIO_PIN_DATA)
        clockPin = gpio.output(GPIO_PIN_CLOCK)
    }

    // MARK: - Internal helper -

    /**
     * Sets color to LEDs in given range.
     *
     * @param color New color to set
     * @param range Range of LEDs that shall be updated. Default value: all
     */
    fun setColor(color: Color, range: IntRange = 0..<NUMBER_OF_LEDS) {
        // Validate range.
        if (range.first < 0 || range.last > NUMBER_OF_LEDS) {
            print("Given LED range is out of bounds.")
            return
        }

        // Update LEDs in range.
        for (i in range) {
            leds[i].setColor(color)
        }

        // Write updated LED values.
        writeLedsValues()
    }

    /**
     * Closes and cleans the connections and pins
     * Must be called if program has been finished.
     */
    fun close() {
        turnAllOff()
        writeLedsValues()
        dataPin.close()
        clockPin.close()
        println("APA102 has been closed.")
    }

    // MARK: - Private helper -

    /**
     * Turn all LEDs on.
     */
    private fun turnAllOn() {
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
     * Updates the clock to locked or unlocked state.
     *
     * @param locked State that shall be applied.
     */
    private fun setClockState(locked: Boolean) {
        // Determine which numer of pulses are required
        val numberOfPulses =
            if (locked) NUMBER_OF_CLOCK_LOCK_PULSES else NUMBER_OF_CLOCK_UNLOCK_PULSES

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

        // Write LEDs down to hardware
        leds.forEach {
            val color = it.color
            val brightness = (31.0 * color.brightness).toInt() and 31
            writeByte((224 or brightness).toByte())
            writeByte(color.blue.toByte())
            writeByte(color.green.toByte())
            writeByte(color.red.toByte())
        }

        setClockState(false)
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
        private const val GPIO_PIN_DATA = 23

        /** Clock BCM GPIO pin numbers.*/
        private const val GPIO_PIN_CLOCK = 24

        /**  Number of available leds on the APA102 (Blinkt).*/
        private const val NUMBER_OF_LEDS = 8

        /** Number of pulses that is required to lock the clock.*/
        private const val NUMBER_OF_CLOCK_LOCK_PULSES = 36

        /** Number of pulses that is required to release the clock.*/
        private const val NUMBER_OF_CLOCK_UNLOCK_PULSES = 32
    }
}

