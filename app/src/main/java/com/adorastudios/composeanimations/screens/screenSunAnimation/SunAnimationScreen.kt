package com.adorastudios.composeanimations.screens.screenSunAnimation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.adorastudios.composeanimations.composables.ScreenLayout
import kotlin.math.abs

@Composable
fun SunAnimationScreen(
    navController: NavController,
) {
    ScreenLayout(
        title = "SunAnimationScreen",
        onBack = { navController.popBackStack() },
    ) {
        var day by remember { mutableStateOf(true) }
        val transition = updateTransition(day, label = "selected state")

        val sunPosition by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 500) },
            label = "sunPosition",
        ) { isDay ->
            if (isDay) 1f else -1f
        }
        val sunColor by transition.animateColor(
            transitionSpec = { tween(durationMillis = 500) },
            label = "sunColor",
        ) { isDay ->
            if (isDay) Color(0xffffff44) else Color(0xff777777)
        }
        val groundColor by transition.animateColor(
            transitionSpec = { tween(durationMillis = 500) },
            label = "groundColor",
        ) { isDay ->
            if (isDay) Color(0xff88cc77) else Color(0xff113322)
        }
        val skyColor by transition.animateColor(
            transitionSpec = { tween(durationMillis = 500) },
            label = "skyColor",
        ) { isDay ->
            if (isDay) Color(0xffbbddff) else Color(0xff333333)
        }
        val cloudColor by transition.animateColor(
            transitionSpec = { tween(durationMillis = 500) },
            label = "cloudColor",
        ) { isDay ->
            if (isDay) Color(0xffffffff) else Color(0xff555555)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = if (day) "DAY" else "NIGHT")
        }
        Spacer(modifier = Modifier.weight(1f))
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(skyColor)
                .align(Alignment.CenterHorizontally),
        ) {
            val dp5 = 5.dp.roundToPx().toFloat()
            val dp10 = 10.dp.roundToPx().toFloat()
            val dp15 = 15.dp.roundToPx().toFloat()
            val dp20 = 20.dp.roundToPx().toFloat()
            val dp30 = 30.dp.roundToPx().toFloat()
            val dp45 = 45.dp.roundToPx().toFloat()
            val dp55 = 55.dp.roundToPx().toFloat()
            val dp70 = 70.dp.roundToPx().toFloat()
            val size = 200.dp.roundToPx().toFloat()

            val sunRadius = if (sunPosition >= 0) dp30 else dp20
            drawCircle(
                color = sunColor,
                radius = sunRadius,
                center = Offset(size / 2, size - (abs(sunPosition) * size / 3 * 2)),
            )
            drawRoundRect(
                color = cloudColor,
                topLeft = Offset(size / 2 + dp5, size / 2 - dp5),
                size = Size(dp70, dp20),
                cornerRadius = CornerRadius(dp20, dp20),
            )
            drawCircle(
                color = cloudColor,
                radius = dp15,
                center = Offset(size / 2 + dp30, size / 2 - dp5),
            )
            drawCircle(
                color = cloudColor,
                radius = dp10,
                center = Offset(size / 2 + dp45, size / 2 - dp10),
            )
            drawCircle(
                color = cloudColor,
                radius = dp10,
                center = Offset(size / 2 + dp55, size / 2 - dp5),
            )
            drawRect(
                color = groundColor,
                topLeft = Offset(0f, 160.dp.roundToPx().toFloat()),
                size = Size(size, 40.dp.roundToPx().toFloat()),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                day = !day
            }) {
                Text(text = "START")
            }
        }
    }
}
