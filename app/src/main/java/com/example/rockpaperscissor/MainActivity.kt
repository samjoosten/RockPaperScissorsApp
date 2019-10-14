package com.example.rockpaperscissor

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.rockpaperscissor.database.GameRepository
import com.example.rockpaperscissor.model.Game
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var gameRepository: GameRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        btnHistory.setOnClickListener { viewGameHistory() }
        btnScissor.setOnClickListener { simulateGame(Action.Scissors) }
        btnPaper.setOnClickListener { simulateGame(Action.Paper) }
        btnRock.setOnClickListener { simulateGame(Action.Rock) }

    }

    private fun viewGameHistory() {
        val intent = Intent(this, HistoryList::class.java)
        startActivity(intent)
    }

    private fun insertGameIntoDatabase(game: Game) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }
        }
    }

    private fun simulateGame(action: Action) {
        val computerPick = (0..2).random()
        val computerAction = when(computerPick) {
            0 -> Action.Rock
            1 -> Action.Paper
            2 -> Action.Scissors
            else -> Action.Paper
        }

        var game = Game(null, "", Date(), computerAction, action)

        game = determineWinner(game)
        insertGameIntoDatabase(game)
        tvWin.text = game.result
        ivComputer.setImageDrawable(getDrawable(game.computerPick.drawableResId))
        ivPlayer.setImageDrawable(getDrawable(game.playerPick.drawableResId))
    }

    private fun determineWinner(game: Game): Game {
        game.result = when {
            game.computerPick == game.playerPick -> getString(R.string.draw_text)
            game.computerPick == Action.Rock && game.playerPick == Action.Scissors -> getString(R.string.lose_text)
            game.computerPick == Action.Scissors && game.playerPick == Action.Paper -> getString(R.string.lose_text)
            game.computerPick == Action.Paper && game.playerPick == Action.Rock -> getString(R.string.lose_text)
            else -> getString(R.string.win_text)
        }

        return game
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
