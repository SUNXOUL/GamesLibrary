package com.sagrd.GamesLibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sagrd.GamesLibrary.domain.model.Category
import com.sagrd.GamesLibrary.domain.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(category: Game)

    @Query(
        """
        SELECT * 
        FROM categories 
        WHERE categoryid=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): Category

    @Delete
    suspend fun delete(category: Game)

    @Query("SELECT * FROM Categories")
    fun getAll(): Flow<List<Category>>
}