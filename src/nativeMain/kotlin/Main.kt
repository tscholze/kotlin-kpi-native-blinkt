import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.lightmodes.cycle
import io.ktgp.Gpio
import io.ktgp.use

/**
 * Entry point of the app that will be
 * launched on program start.
 */
fun main() {
    // 1. Create GPIO context with auto-release after use
    Gpio().use { gpio ->
        // Create APA102 instance
        val apa102 = APA102(gpio)

        // Run it until user presses a key to exit.
        apa102.run {
            apa102.cycle()
            println("Press any key to exit")
            readln()
            apa102.close()
        }
    }
}