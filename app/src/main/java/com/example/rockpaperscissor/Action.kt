package com.example.rockpaperscissor

import androidx.annotation.DrawableRes

enum class Action(val value: Int, val drawableResId: Int) {
    Rock(0, R.drawable.rock),
    Paper(1, R.drawable.paper),
    Scissors(2, R.drawable.scissors)
}