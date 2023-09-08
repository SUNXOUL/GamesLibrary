package com.sagrd.GamesLibrary.ui.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagrd.GamesLibrary.data.GameDB
import com.sagrd.GamesLibrary.domain.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val gamedb: GameDB
) : ViewModel() {
    var name by mutableStateOf ("")

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    val categories: StateFlow<List<Category>> = gamedb.categoryDao().getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun Save() {
        viewModelScope.launch {
            val category: Category = Category(
                name = name
            )
            gamedb.categoryDao().save(category)
            limpiar()
        }
    }
    fun DeleteFactura() {
        viewModelScope.launch {
            val category: Category = Category(
                name = name,
            )
            gamedb.categoryDao().delete(category)
            limpiar()
        }
    }

    private fun limpiar() {
        name = ""
    }
}