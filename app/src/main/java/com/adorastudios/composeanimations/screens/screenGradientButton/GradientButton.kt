package com.adorastudios.composeanimations.screens.screenGradientButton

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun GradientButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    colors: GradientButtonColors = GradientButtonColors(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable (RowScope.() -> Unit),
) {
    val secondColor = remember {
        Animatable(0f)
    }
    val largeRadialGradient = remember(secondColor.value) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val biggerDimension = maxOf(size.height, size.width)
                val smallerDimension = minOf(size.height, size.width)
                return RadialGradientShader(
                    colors = listOf(colors.pressed, colors.enabled),
                    center = size.center,
                    radius = biggerDimension / 2f * secondColor.value + smallerDimension / 2f,
                    colorStops = listOf(secondColor.value, 1f),
                )
            }
        }
    }

    Row(
        modifier = modifier
            .clip(shape)
            .then(
                if (border != null) {
                    Modifier.border(border, shape)
                } else {
                    Modifier
                },
            )
            .then(
                if (enabled) {
                    Modifier
                        .pointerInput(true) {
                            detectTapGestures(
                                onPress = {
                                    secondColor.animateTo(
                                        targetValue = 1f,
                                        animationSpec = tween(500),
                                    )
                                    secondColor.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(500),
                                    )
                                },
                                onTap = {
                                    onClick()
                                },
                            )
                        }
                        .background(
                            largeRadialGradient,
                        )
                } else {
                    Modifier.background(
                        color = colors.disabled,
                    )
                },
            )
            .padding(contentPadding),
    ) {
        content()
    }
}
