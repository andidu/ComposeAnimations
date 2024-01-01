package com.adorastudios.composeanimations.screens.screenDrawHeart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DrawHeartScreen(
    navController: NavController,
) {
    ScreenLayout(
        title = "DrawHeartScreen",
        onBack = {
            navController.popBackStack()
        },
    ) {
        val scope = rememberCoroutineScope()

        val drawPathAnimation = remember {
            Animatable(0f)
        }
        val fillAnimation = remember {
            Animatable(0f)
        }
        val sizeAnimation = remember {
            Animatable(1f)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            val density = LocalDensity.current
            val fullGraphPath = remember(density) {
                val size = Size(
                    with(density) { 100.dp.roundToPx().toFloat() },
                    with(density) { 100.dp.roundToPx().toFloat() },
                )
                val path = Path()
                path.moveTo(size.width / 2, size.height / 3)

                // top right
                path.cubicTo(
                    size.width / 2,
                    0f,
                    size.width,
                    0f,
                    size.width,
                    size.height / 3,
                )

                // bottom right
                path.cubicTo(
                    size.width,
                    size.height * 2 / 3,
                    size.width / 2,
                    size.height,
                    size.width / 2,
                    size.height,
                )

                // bottom left
                path.cubicTo(
                    size.width / 2,
                    size.height,
                    0f,
                    size.height * 2 / 3,
                    0f,
                    size.height / 3,
                )

                // top left
                path.cubicTo(
                    0f,
                    0f,
                    size.width / 2,
                    0f,
                    size.width / 2,
                    size.height / 3,
                )
                path
            }

            val pathMeasure = remember {
                PathMeasure()
            }
            val animatedPath = remember {
                derivedStateOf {
                    val newPath = Path()
                    pathMeasure.setPath(fullGraphPath, false)
                    pathMeasure.getSegment(
                        0f,
                        drawPathAnimation.value * pathMeasure.length,
                        newPath,
                    )
                    newPath
                }
            }
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .graphicsLayer {
                        this.scaleX = sizeAnimation.value
                        this.scaleY = sizeAnimation.value
                    },
            ) {
                drawPath(
                    color = Color.Red,
                    path = animatedPath.value,
                    style = Stroke(width = 10f, join = StrokeJoin.Round),
                )
                drawPath(
                    color = Color.Red.copy(
                        alpha = fillAnimation.value,
                    ),
                    path = fullGraphPath,
                    style = Fill,
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    scope.launch {
                        launch {
                            drawPathAnimation.animateTo(
                                1f,
                                animationSpec = tween(500),
                            )
                        }
                        delay(300)
                        launch {
                            fillAnimation.animateTo(
                                0.5f,
                                animationSpec = tween(500),
                            )
                        }
                        launch {
                            sizeAnimation.animateTo(
                                1.5f,
                                animationSpec = tween(500),
                            )
                        }
                    }
                },
            ) {
                Text(text = "ANIMATE!")
            }
            Button(
                modifier = Modifier.padding(16.dp),
                onClick = {
                    scope.launch {
                        launch {
                            fillAnimation.animateTo(
                                0f,
                                animationSpec = tween(500),
                            )
                        }
                        launch {
                            sizeAnimation.animateTo(
                                1f,
                                animationSpec = tween(500),
                            )
                        }
                        delay(300)
                        drawPathAnimation.animateTo(
                            0f,
                            animationSpec = tween(500),
                        )
                    }
                },
            ) {
                Text(text = "RETURN BACK!")
            }
        }
    }
}
