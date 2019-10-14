package com.example.rockpaperscissor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rockpaperscissor.database.GameRepository
import com.example.rockpaperscissor.model.Game
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.android.synthetic.main.content_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryList : AppCompatActivity() {

    private var games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        gameRepository = GameRepository(this)
        initViews()

    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvGames.adapter = gameAdapter
        rvGames.layoutManager = LinearLayoutManager(this@HistoryList, RecyclerView.VERTICAL, false)
        rvGames.addItemDecoration(DividerItemDecoration(rvGames.context, DividerItemDecoration.VERTICAL))

        getAllGames()


        btnDelete.setOnClickListener { onDeleteClick() }



    }

    private fun getAllGames() {
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryList.games.clear()
            this@HistoryList.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }
    }

    private fun onDeleteClick() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
            getAllGames()
            gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}