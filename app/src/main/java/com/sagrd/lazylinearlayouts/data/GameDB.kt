package com.sagrd.GamesLibrary.data

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sagrd.GamesLibrary.data.local.dao.CategoryDao
import com.sagrd.GamesLibrary.data.local.dao.GameDao
import com.sagrd.GamesLibrary.di.AppModule
import com.sagrd.GamesLibrary.domain.model.Category
import com.sagrd.GamesLibrary.domain.model.Game

@Database(
    entities = [Game::class, Category::class],
    version = 3
)
abstract class GameDB : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun categoryDao(): CategoryDao
}
