package com.jemas87.aoc2023

import android.content.Context

fun Context.puzzle3Part1() = readInput(R.raw.puzzle3).run { sumNumberAdjacentSymbol() }

fun List<String>.sumNumberAdjacentSymbol(): Int {
    var sum = 0

    val rowInfos = map { it.toRowInfo() }

    rowInfos.forEachIndexed { index, rowInfo ->
        val (above, below) = when (index) {
            0 -> null to rowInfos[1]
            size - 1 -> rowInfos[size - 2] to null
            else -> rowInfos[index - 1] to rowInfos[index + 1]
        }

        rowInfo.numberInfos.forEach {
            val sameRow = it.adjacentInSameRow
            val otherRow = it.adjacentToSymbolInOtherRow(above, below)
            if (sameRow || otherRow) sum += it.value
        }
    }

    return sum
}

fun String.toRowInfo(): RowInfo {
    var number = ""
    var startIndex = -1
    var adjacent = false
    val numberInfos = mutableListOf<NumberInfo>()
    val symbolIndices = mutableListOf<Int>()

    forEachIndexed { index, char ->
        if (char.isDigit()) {
            // Build the number
            if (number == "") {
                // First part of the number. Save the start index of this number and
                // check if the previous char was a symbol.
                startIndex = index
                if (index > 0 && get(index - 1).isSymbol) adjacent = true
            }
            number += char
        } else if (char.isSymbol) {
            symbolIndices.add(index)

            if (number != "") {
                // Building a number and reached a symbol. The number is completed and we also know it's
                // adjacent to a symbol.
                numberInfos.add(
                    NumberInfo(
                        startIndex = startIndex,
                        endIndex = index - 1,
                        value = number.toInt(),
                        adjacentInSameRow = true
                    )
                )
                number = ""
                adjacent = false
            }
        } else if (number != "") {
            // Building a number and reached a dot. The number is completed.
            // If there was a symbol before start building the number we have an adjacent number.
            numberInfos.add(
                NumberInfo(
                    startIndex = startIndex,
                    endIndex = index - 1,
                    value = number.toInt(),
                    adjacentInSameRow = adjacent,
                )
            )
            number = ""
            adjacent = false
        }
    }

    return RowInfo(numberInfos = numberInfos, symbolIndices = symbolIndices)
}

data class RowInfo(val numberInfos: List<NumberInfo>, val symbolIndices: List<Int>)

data class NumberInfo(
    val startIndex: Int,
    val endIndex: Int,
    val value: Int,
    val adjacentInSameRow: Boolean,
)

fun NumberInfo.adjacentToSymbolInOtherRow(above: RowInfo?, below: RowInfo?): Boolean {
    val adjacentToAbove = above?.symbolIndices?.any {
        it in startIndex - 1..endIndex + 1
    } ?: false
    val adjacentToBelow = below?.symbolIndices?.any {
        it in startIndex - 1..endIndex + 1
    } ?: false
    return adjacentToAbove || adjacentToBelow
}

val Char.isSymbol get() = !this.isDigit() && this != '.'
