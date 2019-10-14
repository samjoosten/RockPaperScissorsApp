package com.example.rockpaperscissor.model

import android.media.AsyncPlayer
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rockpaperscissor.Action
import java.util.*


@Entity(tableName = "game_table")
data class Game(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "result")
    var result: String,

    @ColumnInfo(name = "time")
    var time: Date,

    @ColumnInfo(name = "computerPick")
    val computerPick: Action,

    @ColumnInfo(name = "playerPick")
    val playerPick: Action

)