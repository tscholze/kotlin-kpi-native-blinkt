import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.apa102.Command
import io.github.tscholze.kblinkt.apa102.Color
import io.github.tscholze.kblinkt.server.runServer
import io.ktgp.Gpio
import io.ktgp.use
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Entry point of the app that will be
 * launched on program start.
 */
fun main() = runBlocking {
    launch {
        //  Create GPIO context with auto-release after use
        Gpio().use { gpio ->
            // Create flow to collect and emit requested actions
            val actions = MutableSharedFlow<Command>()

            // Create APA102 instance
            val apa102 = APA102(gpio, actions)

            // Start server
            val server = runServer(actions)

            // Run it until user presses a key to exit.
            apa102.run {
                println("Start listing")
                apa102.startListing()
            }

            apa102.setColor(Color.Blue)
        }
    }

    print("end")
}
