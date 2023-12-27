package com.adorastudios.composeanimations.screens.screenLazyListWithDragAndDrop

import androidx.compose.runtime.Immutable

@Immutable
data class LazyListWithDragAndDropState(
    val list: List<Int> = List(30) { index -> index },
)
