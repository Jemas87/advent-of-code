package com.jemas87.aoc2023

import android.content.Context

fun Context.puzzle2Part1() = readInput(R.raw.puzzle2).sumOf { line -> line.toGame().takeIf { it.possible }?.id ?: 0 }

fun Context.puzzle2Part2() = readInput(R.raw.puzzle2).sumOf { line -> line.toGame().maxSetPower }

private fun String.toGame(): Game {
    val gameIdAndSets = replace(" ", "").split(":")
    val id = gameIdAndSets[0].filter { it.isDigit() }.toInt()
    val sets = gameIdAndSets[1].split(";").map { it.toSet() }
    return Game(id, sets)
}

private fun String.toSet(): GameSet {
    val set = split(",")
    return GameSet(
        red = set.nbrOfCubes("red"),
        green = set.nbrOfCubes("green"),
        blue = set.nbrOfCubes("blue"),
    )
}

private fun List<String>.nbrOfCubes(color: String) =
    firstOrNull { it.contains(color) }
        ?.filter { it.isDigit() }
        ?.toInt()
        ?: 0

private data class Game(val id: Int, val sets: List<GameSet>)

private data class GameSet(val red: Int, val green: Int, val blue: Int)

private val Game.possible get() = sets.all { it.possible }

private val GameSet.possible get() = red <= RED && green <= GREEN && blue <= BLUE

private const val RED = 12
private const val GREEN = 13
private const val BLUE = 14

private val Game.maxSetPower: Int get() {
    var maxRed = 0
    var maxGreen = 0
    var maxBlue = 0
    sets.forEach {
        if (it.red > maxRed) maxRed = it.red
        if (it.green > maxGreen) maxGreen = it.green
        if (it.blue > maxBlue) maxBlue = it.blue
    }
    return maxRed * maxGreen * maxBlue
}
