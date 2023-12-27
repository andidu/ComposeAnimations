package com.adorastudios.composeanimations.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.Screen

@Composable
fun MainScreen(
    navController: NavController,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screen.LazyListWithDragAndDrop.route)
                },
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "LazyListWithDragAndDrop",
                    textAlign = TextAlign.Center,
                )
            }
        }
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screen.SunAnimation.route)
                },
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "Sun animation",
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
