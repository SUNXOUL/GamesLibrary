package com.sagrd.GamesLibrary.domain.repository

import com.sagrd.GamesLibrary.data.GameDB
import com.sagrd.GamesLibrary.domain.model.Game
import javax.inject.Inject

class TicketRepository @Inject constructor(
    private val gamedb: GameDB
) {
    suspend fun  guardarTicket(game: Game) = gamedb.gameDao().save(game)
    fun getAll() = gamedb.gameDao().getAll()
}