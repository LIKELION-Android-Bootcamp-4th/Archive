package com.likelion.liontalk.features.chatroom.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ExplosionEffect(
    onExploded: () -> Unit
) {
    val bombY = remember { Animatable(-200f)}
    val bombScale = remember {Animatable(0.5f)}
    val explosionScale = remember { Animatable(1f)}
    val explosionAlpah = remember {Animatable(1f)}

    var exploded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        if (!exploded) {
            Text(
                text = "\uD83D\uDCA3",
                fontSize = 50.sp,
                modifier = Modifier.offset { IntOffset(0, bombY.value.toInt()) }
                    .scale(bombScale.value)
            )
        } else {
            Text(
                text = "\uD83D\uDCA5",
                fontSize = 80.sp,
                modifier = Modifier.align(Alignment.Center).scale(explosionScale.value).alpha(explosionAlpah.value)
            )
        }

        LaunchedEffect(true) {
            launch {
                bombY.animateTo(
                    targetValue = 1000f,
                    animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
                )
            }

            launch {
                bombScale.animateTo(
                    targetValue = 2f,
                    animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
                )
            }
            delay(1600)
            exploded = true
            launch {
                explosionScale.animateTo(
                    targetValue = 3f,
                    animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
                )
            }
            launch {
                explosionAlpah.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 800)
                )
            }
            delay(900)
            onExploded()
        }
    }
}