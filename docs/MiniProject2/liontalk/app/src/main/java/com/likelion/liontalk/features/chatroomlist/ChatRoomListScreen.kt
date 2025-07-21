package com.likelion.liontalk.features.chatroomlist

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomListScreen(navController : NavHostController) {

    val context = LocalContext.current
    val viewModel = remember {
        ChatRoomListViewModel(context.applicationContext as Application)
    }

    val state by viewModel.state.observeAsState(ChatRoomListState())

    var newRoomName by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar( title = { Text("채팅방 목록") } )
        },
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(padding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newRoomName,
                        onValueChange = { newRoomName = it},
                        label = { Text("새 채팅방 이름")},
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    )

                    Button(
                        onClick = {
                            if ( newRoomName.isNotBlank()) {
                                // TODO : 실제 방추가 로직 구현
                                viewModel.createChatRoom(newRoomName)
                            }
                        },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text("방 생성")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if ( state.isLoading ) {
                    CircularProgressIndicator()
                } else if (state.chatRooms.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) { Text("채팅방이 없습니다.") }
                } else {
                     LazyColumn {
                        items(state.chatRooms) { room ->
                            Text(text = room.title )
                        }
                    }
                }
            }
        }

    )
}