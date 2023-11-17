import io.ktgp.gpio.Gpio
import io.ktgp.gpio.Output
import io.ktgp.gpio.PinState

class APA102(gpio: Gpio) {
    // MARK: - Private properties -

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

    /** List of attached LEDs **/
    private var leds = mutableListOf<APA102LED>()

    /** GPIO pin for data connections */
    private val dataPin: Output

    /** GPIO pin to access the clock */
    private val clockPin: Output

    // MARK: - Init -

    init {
        println("Init")
        repeat(NUMBER_OF_LEDS) {
            leds.add(APA102LED())
        }

        dataPin = gpio.output(GPIO_PIN_DATA)
        clockPin = gpio.output(GPIO_PIN_CLOCK)

        turnAllOn()
    }

    fun close() {
        println("Close")
        turnAllOff()
        writeLedsValues()
        dataPin.close()
        clockPin.close()
    }

    // MARK: - Private helper -

    private fun turnAllOn() {
        println("turnAllOn")
        leds.forEach { it.turnOn() }
        writeLedsValues()
    }

    private fun turnAllOff() {
        leds.forEach { it.turnOff() }
    }

    private fun setClockState(locked: Boolean) {
        val numberOfPulses = if (locked) NUMBER_OF_CLOCK_LOCK_PULSES else NUMBER_OF_CLOCK_UNLOCK_PULSES

        dataPin.setState(PinState.LOW)

        repeat(numberOfPulses) {
            clockPin.setState(PinState.HIGH)
            clockPin.setState(PinState.LOW)
        }
    }

    private fun writeLedsValues() {
        setClockState(true)
        for (led in leds) {
            writeLED(led)
        }
        setClockState(false)
    }

    private fun writeLED(led: APA102LED) {
        val sendBright = (31.0 * led.brightness).toInt() and 31
        writeByte((224 or sendBright).toByte())
        writeByte(led.blue.toByte())
        writeByte(led.green.toByte())
        writeByte(led.red.toByte())
    }

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
}

class APA102LED(
    var red: Int = 1,
    var green: Int = 1,
    var blue: Int = 1,
    var brightness: Double = 1.0
) {
    // MARK: - Internal LED helpers -

    fun setBrightness(value: Double) {
        if (value.isPercentValue()) {
            brightness = value
            return
        } else {
            println("Error")
        }
    }

    fun setRed(value: Int) {
        if (value.isHexValue()) {
            red = value
        } else {
            println("Error")
        }
    }

    fun setGreen(value: Int) {
        if (value.isHexValue()) {
            green = value
        } else {
            println("Error")
        }
    }

    fun setBlue(value: Int) {
        if (value.isHexValue()) {
            blue = value
        } else {
            println("Error")
        }
    }

    fun setRgb(red: Int, green: Int, blue: Int, brightness: Double = 1.0) {
        setBrightness(brightness)

        setRed(red)
        setGreen(green)
        setBlue(blue)
    }

    fun turnOn() {
        setRgb(
            255,
            255,
            255,
            1.0
        )
    }

    fun turnOff() {
        setRgb(
            0,
            0,
            0,
            0.0
        )
    }
}


fun Double.isPercentValue(): Boolean {
    return !(this < 0 || this > 1)
}

fun Int.isHexValue(): Boolean {
    return !(this < 0 || this > 255)
}