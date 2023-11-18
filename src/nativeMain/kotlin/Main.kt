import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.apa102.Action
import io.github.tscholze.kblinkt.lightmodes.cycle
import io.github.tscholze.kblinkt.server.runServer
import io.ktgp.Gpio
import io.ktgp.use
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Entry point of the app that will be
 * launched on program start.
 */
fun main() {
    // 0. Property definitions
    val actions = MutableSharedFlow<Action>()

    // 1. Create GPIO context with auto-release after use
    Gpio().use { gpio ->
        // Create APA102 instance
        val apa102 = APA102(gpio, actions)

        // Run it until user presses a key to exit.
        apa102.run {
            apa102.cycle()
            println("Press any key to exit")
            readln()
            apa102.close()
        }
    }

    // 2. Start server
    runServer()
}