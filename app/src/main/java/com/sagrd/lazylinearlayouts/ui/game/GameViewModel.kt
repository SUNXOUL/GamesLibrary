package com.sagrd.GamesLibrary.ui.game

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagrd.GamesLibrary.data.GameDB
import com.sagrd.GamesLibrary.domain.model.Game
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gamedb: GameDB
) : ViewModel() {
    var name : String by mutableStateOf("${LocalDate.now()}")
    var categoryid : Int by mutableStateOf (0)
    var image by mutableStateOf<Uri?> (null)



    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val games: StateFlow<List<Game>> = gamedb.gameDao().getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun SaveGame() {
        viewModelScope.launch {
            val game = Game(
                name = name,
                categoryid = categoryid,
                image = image,
            )
            gamedb.gameDao().save(game)
            limpiar()
        }
    }
    fun DeleteFactura() {
        viewModelScope.launch {
            val game = Game(
                name = name,
                categoryid = categoryid,
                image = image,
            )
            gamedb.gameDao().delete(game)
            limpiar()
        }
    }

    private fun limpiar() {
        name = ""
        categoryid = 0
    }
}