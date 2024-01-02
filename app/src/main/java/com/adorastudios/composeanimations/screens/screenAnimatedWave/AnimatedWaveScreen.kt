package com.adorastudios.composeanimations.screens.screenAnimatedWave

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun AnimatedWaveScreen(
    navController: NavController,
) {
    ScreenLayout(
        title = "AnimatedWaveScreen",
        onBack = { navController.popBackStack() },
    ) {
        var waveState by remember {
            mutableStateOf(WaveAnimationState())
        }

        LaunchedEffect(key1 = Unit) {
            while (true) {
                waveState = waveState.copy(
                    point0 = Random.nextFloat() / 10 * 6 - 0.3f,
                    point1 = waveState.point0,
                    point2 = waveState.point1,
                    point3 = waveState.point2,
                    point4 = waveState.point3,
                    point5 = waveState.point4,
                    point6 = waveState.point5,
                )
                delay(550)
            }
        }

        val waveAnimation = updateTransition(targetState = waveState, label = "wave")
        val wave0 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave0",
        ) { it.point0 }
        val wave1 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave1",
        ) { it.point1 }
        val wave2 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave2",
        ) { it.point2 }
        val wave3 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave3",
        ) { it.point3 }
        val wave4 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave4",
        ) { it.point4 }
        val wave5 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave5",
        ) { it.point5 }
        val wave6 = waveAnimation.animateFloat(
            transitionSpec = { tween(500, easing = LinearEasing) },
            label = "wave6",
        ) { it.point6 }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            val density = LocalDensity.current
            val size = Size(
                with(density) { 100.dp.roundToPx().toFloat() },
                with(density) { 100.dp.roundToPx().toFloat() },
            )

            val mediumColoredPoint0 =
                Offset(x = -size.width * 0.25f, y = size.height * (0.5f + wave0.value))
            val mediumColoredPoint1 =
                Offset(x = 0f, y = size.height * (0.5f + wave1.value))
            val mediumColoredPoint2 =
                Offset(x = size.width * 0.25f, y = size.height * (0.5f + wave2.value))
            val mediumColoredPoint3 =
                Offset(x = size.width * 0.5f, y = size.height * (0.5f + wave3.value))
            val mediumColoredPoint4 =
                Offset(x = size.width * 0.75f, y = size.height * (0.5f + wave4.value))
            val mediumColoredPoint5 =
                Offset(x = size.width, y = size.height * (0.5f + wave5.value))
            val mediumColoredPoint6 =
                Offset(x = size.width * 1.25f, y = size.height * (0.5f + wave6.value))

            val mediumColoredPath = Path().apply {
                moveTo(mediumColoredPoint0.x, mediumColoredPoint0.y)
                waveCurve(mediumColoredPoint0, mediumColoredPoint1)
                waveCurve(mediumColoredPoint1, mediumColoredPoint2)
                waveCurve(mediumColoredPoint2, mediumColoredPoint3)
                waveCurve(mediumColoredPoint3, mediumColoredPoint4)
                waveCurve(mediumColoredPoint4, mediumColoredPoint5)
                waveCurve(mediumColoredPoint5, mediumColoredPoint6)
                lineTo(size.width * 1.25f, size.height + 100f)
                lineTo(-size.width * 0.25f, size.height + 100f)
                close()
            }
            Canvas(
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.Blue.copy(alpha = 0.5f)),
            ) {
                drawPath(
                    color = Color.White,
                    path = mediumColoredPath,
                    style = Stroke(width = 10f, join = StrokeJoin.Round),
                )
                drawPath(
                    color = Color.Blue,
                    path = mediumColoredPath,
                    style = Fill,
                )
            }
        }
    }
}

fun Path.waveCurve(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        (from.x + to.x) / 2f,
        (from.y + to.y) / 2f,
    )
}
