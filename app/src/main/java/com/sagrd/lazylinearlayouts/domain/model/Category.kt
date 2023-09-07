package com.sagrd.GamesLibrary.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class Category(
    @PrimaryKey
    val categoryid : Int?=null ,
    var name :String="",
)