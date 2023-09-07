package com.sagrd.GamesLibrary.domain.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Games")
data class Game(
    @PrimaryKey
    val gameId: Int?=null,
    var name:String="",
    var image: Uri?,
    var categoryid: Int?=null
)

