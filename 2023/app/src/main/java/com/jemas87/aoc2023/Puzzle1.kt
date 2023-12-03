package com.jemas87.aoc2023

import android.content.Context

fun Context.puzzle1Part1() = readInput(R.raw.puzzle1).sumOf { line -> line.filter { it.isDigit() }.sumFirstLast() }

fun Context.puzzle1Part2() = readInput(R.raw.puzzle1).sumOf { line -> line.firstLastDigit().sumFirstLast() }

private fun String.sumFirstLast() = "${first()}${last()}".toInt()

private fun String.firstLastDigit(): String {
    val matches = pattern.findAll(this)
    val first = matches.first().value
    val firstDigit = if (first.length > 1) digitMap[first] else first

    return when (matches.count()) {
        1 -> "$firstDigit$firstDigit"

        else -> {
            val reversedMatches = reversedPattern.findAll(this.reversed())
            val reversedFirst = reversedMatches.first().value.reversed()
            val lastDigit = if (reversedFirst.length > 1) digitMap[reversedFirst] else reversedFirst
            "$firstDigit$lastDigit"
        }
    }
}

private val digitMap = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

private val digitWords = digitMap.keys.joinToString("|")
private val reversedDigitWords: String = digitMap.keys.joinToString("|") { it.reversed() }

private val pattern = "\\d|$digitWords".toRegex()
private val reversedPattern = "\\d|$reversedDigitWords".toRegex()
