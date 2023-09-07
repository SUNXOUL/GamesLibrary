package com.sagrd.GamesLibrary.di

import android.content.Context
import androidx.room.Room
import com.sagrd.GamesLibrary.data.GameDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn ( SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTicketDatabase(@ApplicationContext appContext: Context): GameDB =
        Room.databaseBuilder(
            appContext,
            GameDB::class.java,
            "Ticket.db")
            .fallbackToDestructiveMigration()
            .build()
}