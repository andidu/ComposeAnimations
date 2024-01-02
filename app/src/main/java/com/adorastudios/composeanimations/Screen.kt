package com.adorastudios.composeanimations

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object LazyListWithDragAndDrop : Screen("lazy_list_with_drag_and_drop")
    object SunAnimation : Screen("sun_animation")
    object SideBottomSheet : Screen("side_bottom_sheet")
    object DrawHeart : Screen("draw_heart_screen")
    object AnimatedWave : Screen("animate_wave")
    object GradientButton : Screen("gradient_button")
}
