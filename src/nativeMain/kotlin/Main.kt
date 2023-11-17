import io.ktgp.Gpio
import io.ktgp.sleep
import io.ktgp.use

fun main() {


    Gpio().use { gpio ->
        val apa102 = APA102(gpio)
        apa102.run {
            apa102.cycle()
            println("Press any key to exit")
            readln()
            apa102.close()
        }

    }
}