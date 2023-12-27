package com.adorastudios.composeanimations.screens.screenLazyListWithDragAndDrop

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LazyListWithDragAndDropViewModel : ViewModel() {
    private val _state: MutableState<LazyListWithDragAndDropState> =
        mutableStateOf(LazyListWithDragAndDropState())
    val state: State<LazyListWithDragAndDropState> = _state

    // simply changing 2 values in array will not work -
    // drag distance might me bigger than element's height
    fun move(index1: Int, index2: Int) {
        val list = mutableListOf<Int>()
        list.addAll(state.value.list.toMutableList())
        val tmp = list.removeAt(index1)
        list.add(index2, tmp)
        _state.value = state.value.copy(
            list = list,
        )
    }
}
