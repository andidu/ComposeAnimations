@file:OptIn(ExperimentalAnimationApi::class)

package com.adorastudios.composeanimations.screens.screenSideBottomSheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout

@Composable
fun SideBottomSheetScreen(
    navController: NavController,
) {
    ScreenLayout(
        title = "SideBottomSheetScreen",
        onBack = { navController.popBackStack() },
    ) {
        val randomList1 by remember {
            mutableStateOf(List(100) { it }.shuffled())
        }
        val randomList2 by remember {
            mutableStateOf(List(100) { it }.shuffled())
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
        ) {
            val screenHeight = maxHeight
            val screenWidth = maxWidth

            var bottomSheetOpen by remember {
                mutableStateOf(false)
            }
            val transition = updateTransition(
                targetState = bottomSheetOpen,
                label = "transition",
            )
            val borderColor by transition.animateColor(
                label = "borderColor",
            ) { open ->
                if (open) MaterialTheme.colorScheme.background else Color.Transparent
            }
            val bottomSheetHeight by transition.animateDp(
                label = "bottomSheetHeight",
            ) { open ->
                if (open) screenHeight else 68.dp
            }
            val bottomSheetWidth by transition.animateDp(
                label = "bottomSheetWidth",
            ) { open ->
                if (open) screenWidth else 68.dp
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.TopStart,
            ) {
                transition.AnimatedVisibility(
                    visible = { open -> !open },
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(
                            items = randomList2,
                        ) {
                            Text(text = "Item $it")
                        }
                    }
                }
                transition.AnimatedVisibility(
                    visible = { open -> open },
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {
                            bottomSheetOpen = false
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null,
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .size(height = bottomSheetHeight, width = bottomSheetWidth)
                    .align(Alignment.BottomEnd)
                    .clip(CutCornerShape(topStart = 68.dp))
                    .border(
                        color = borderColor,
                        width = 4.dp,
                        shape = CutCornerShape(topStart = 68.dp),
                    )
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center,
            ) {
                transition.AnimatedVisibility(
                    visible = { open -> open },
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 68.dp, vertical = 4.dp),
                    ) {
                        items(
                            items = randomList1,
                        ) {
                            Text(text = "Item $it")
                        }
                    }
                }
                transition.AnimatedVisibility(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.BottomEnd),
                    visible = { open -> !open },
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {
                            bottomSheetOpen = true
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}
