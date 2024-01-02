package com.adorastudios.composeanimations.screens.screenGradientButton

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout

@Composable
fun GradientButtonScreen(
    navController: NavController,
) {
    ScreenLayout(
        title = "GradientButton",
        onBack = { navController.popBackStack() },
    ) {
        GradientButton(
            onClick = {},
            contentPadding = PaddingValues(16.dp),
            shape = MaterialTheme.shapes.extraSmall,
        ) {
            Text(text = "B 1")
        }
        GradientButton(
            onClick = {},
            contentPadding = PaddingValues(32.dp),
            shape = MaterialTheme.shapes.extraSmall,
        ) {
            Text(text = "B 2")
        }
        GradientButton(
            onClick = {},
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(text = "B 3")
        }
        GradientButton(
            onClick = {},
            contentPadding = PaddingValues(32.dp),
        ) {
            Text(text = "B 4")
        }
    }
}
