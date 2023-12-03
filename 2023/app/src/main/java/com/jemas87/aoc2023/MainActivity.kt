package com.jemas87.aoc2023

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Puzzle1Part1", puzzle1Part1().toString())
        Log.d("Puzzle1Part2", puzzle1Part2().toString())

        Log.d("Puzzle2Part1", puzzle2Part1().toString())
        Log.d("Puzzle2Part2", puzzle2Part2().toString())

        Log.d("Puzzle3Part1", puzzle3Part1().toString())
    }
}
