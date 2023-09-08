package com.sagrd.GamesLibrary.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Games")
data class Game(
    @PrimaryKey
    val gameId: Int?=null,
    var name:String="",
    var image: String,
    var categoryid: Int?=null,
)

