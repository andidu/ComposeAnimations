package com.adorastudios.composeanimations

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object LazyListWithDragAndDrop : Screen("lazy_list_with_drag_and_drop")
}
