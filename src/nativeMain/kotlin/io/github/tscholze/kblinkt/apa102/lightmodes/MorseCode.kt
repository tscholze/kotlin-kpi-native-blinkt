package io.github.tscholze.kblinkt.apa102.lightmodes

import io.github.tscholze.kblinkt.apa102.APA102
import io.github.tscholze.kblinkt.apa102.Color
import io.ktgp.util.sleep

fun APA102.morseCode(text: String) {
    val code = text.uppercase().toMorseCode()

    code.forEach {
        when (it) {
            MorseCodeFragment.LONG -> {
                setColor(Color.White)
                sleep(500)
                setColor(Color.Black)
            }

            MorseCodeFragment.SHORT -> {
                setColor(Color.White)
                sleep(200)
                setColor(Color.Black)
            }

            MorseCodeFragment.PAUSE -> sleep(500)
        }
    }
}

private fun String.toMorseCode(): List<MorseCodeFragment> {
    val code = mutableListOf<MorseCodeFragment>()

    this.forEachIndexed { index, char ->
        morseCodeFragmentMapping[char]?.let { it1 -> code.addAll(it1) }

        // Add space between chars but not after the last one.
        if (index != this.count() - 1) {
            code.add(MorseCodeFragment.PAUSE)
        }
    }

    return code
}

/**
 * Character (A-Z) mapping from char to list of morse code fragments
 */
private val morseCodeFragmentMapping: Map<Char, List<MorseCodeFragment>> = mapOf(
    'A' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG),
    'B' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT),
    'C' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'D' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT),
    'E' to listOf(MorseCodeFragment.SHORT),
    'F' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'G' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'H' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'I' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.SHORT),
    'J' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.LONG),
    'K' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.LONG),
    'L' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT),
    'M' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG),
    'N' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'O' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.LONG),
    'P' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'Q' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.LONG),
    'R' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.SHORT),
    'S' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT),
    'T' to listOf(MorseCodeFragment.LONG),
    'U' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.LONG),
    'V' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT, MorseCodeFragment.LONG),
    'W' to listOf(MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.LONG),
    'Y' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.LONG, MorseCodeFragment.LONG),
    'Z' to listOf(MorseCodeFragment.LONG, MorseCodeFragment.LONG, MorseCodeFragment.SHORT, MorseCodeFragment.SHORT)
)

enum class MorseCodeFragment {
    LONG,
    SHORT,
    PAUSE,
}
