package com.likelion.liontalk.features.chatroom

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(roomId: Int){
    val context = LocalContext.current
    val viewModel = remember {
        ChatRoomViewModel(context.applicationContext as Application,roomId)
    }

    val messages by viewModel.messages.observeAsState(emptyList())
    var inputMessage by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("채팅방 #$roomId")})
        },
        content = { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
                    .navigationBarsPadding()
            ) {
                LazyColumn(modifier = Modifier.weight(1f)
                    .padding(8.dp)) {
                    items(messages) { message ->
                        // TODO
                        Text("${message.content} (${message.sender})")
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    TextField(
                        value = inputMessage,
                        onValueChange = {inputMessage = it},
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("메세지를 입력하세요")}
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            if(inputMessage.isNotBlank()) {
                                viewModel.sendMessage("gabseok",inputMessage)
                                inputMessage = ""
                                keyboardController?.hide()

                            }
                        },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Text("전송")
                    }
                }
            }
        }
    )
}