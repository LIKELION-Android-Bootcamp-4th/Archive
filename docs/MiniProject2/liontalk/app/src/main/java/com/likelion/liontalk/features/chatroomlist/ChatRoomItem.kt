package com.likelion.liontalk.features.chatroomlist

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.likelion.liontalk.common.utils.DateFormatter
import com.likelion.liontalk.data.local.entity.ChatRoomEntity
import com.likelion.liontalk.model.ChatRoom
import java.time.format.DateTimeFormatter

@Composable
fun ChatRoomItem(room: ChatRoom,
                 isOwner : Boolean,
                 onClick: (ChatRoom) -> Unit,
                 onLongPressDelete:(ChatRoom) -> Unit,
                 onLongPressLock:(ChatRoom) -> Unit) {

    Card(
       modifier = Modifier.fillMaxWidth().padding(16.dp)
           .combinedClickable (
               onClick = { onClick(room)},
               onLongClick = {}
           ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Owner Avatar
            SubcomposeAsyncImage(
                model = room.owner.avataUrl,
                contentDescription = "avatar",
                modifier = Modifier.size(48.dp)
                    .clip(CircleShape)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            if (isOwner) {
//                                showLockDialog = true
                            }
                        }
                    )
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                    else -> Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "default icon",
                        tint = Color.Gray,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(4.dp))


            Column(modifier = Modifier.padding(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isOwner) {
                        Box(
                            modifier = Modifier.background(
                                Color(0xFF1976D2),
                                shape = RoundedCornerShape(4.dp)
                            )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "오너",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        Text(
                            text = room.owner.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${room.users.size}명",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = room.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

//                if (room.un)

                }

            }

        }
    }
}

