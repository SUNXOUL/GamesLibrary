package com.sagrd.GamesLibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sagrd.GamesLibrary.domain.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(game: Game)

    @Query(
        """
        SELECT * 
        FROM Games 
        WHERE gameId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): Game

    @Delete
    suspend fun delete(game: Game)

    @Query("SELECT * FROM Games")
    fun getAll(): Flow<List<Game>>
}