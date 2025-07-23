package com.likelion.liontalk.features.chatroom.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SystemMessageItem(message: String) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center

    ) {
        Text(
            text = message,
            color = Color.Gray,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
    }
}