package com.sagrd.GamesLibrary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sagrd.GamesLibrary.data.local.dao.CategoryDao
import com.sagrd.GamesLibrary.data.local.dao.GameDao
import com.sagrd.GamesLibrary.domain.model.Category
import com.sagrd.GamesLibrary.domain.model.Game

@Database(
    entities = [Game::class, Category::class],
    version = 2
)
abstract class GameDB : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun categoryDao(): CategoryDao
}
