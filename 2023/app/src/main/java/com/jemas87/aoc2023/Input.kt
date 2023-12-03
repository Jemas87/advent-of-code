package com.jemas87.aoc2023

import android.content.Context
import androidx.annotation.RawRes

fun Context.readInput(@RawRes id: Int) = resources
    .openRawResource(id)
    .bufferedReader()
    .readLines()