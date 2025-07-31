package com.likelion.liontalk.features.chatroom


import android.app.Application
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.likelion.liontalk.common.sound.SoundPlayer
import com.likelion.liontalk.common.sound.SoundType
import com.likelion.liontalk.features.chatroom.components.ChatMessageItem
import com.likelion.liontalk.features.chatroom.components.ChatRoomSettingContent
import com.likelion.liontalk.features.chatroom.components.ExplosionEffect
import com.likelion.liontalk.model.ChatUser
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(navController: NavController, roomId: String){
    val context = LocalContext.current
//    val viewModel = remember {
//        ChatRoomViewModel(context.applicationContext as Application,roomId)
//    }
    val viewModel : ChatRoomViewModel = viewModel(
        factory = ChatRoomViewModelFactory(context.applicationContext as Application,roomId)
    )

//    val messages by viewModel.messages.observeAsState(emptyList())    //for LiveData
    val messages by viewModel.messages.collectAsState() //for Flow
    val inputMessage = remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    val typingUser = remember {mutableStateOf<String?>(null)}
    val eventFlow = viewModel.event
    var showLeaveDialog by remember { mutableStateOf(false) }

    // 채팅방 설정창
    var showSettingsPanel by remember { mutableStateOf(false)}

    // 추방 다이얼로그
    var showKickDialog by remember { mutableStateOf(false) }
    var kickTarget by remember { mutableStateOf<ChatUser?>(null)}

    var showExplodDialog by remember { mutableStateOf(false) }
    val explodedState by viewModel.explodeState.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    fun playSentSound(soundtype: SoundType) {
        val soundPlayer = SoundPlayer(context)
        soundPlayer.play(soundtype)
    }

    LaunchedEffect(Unit) {
        eventFlow.collectLatest { event ->
            when(event) {
                is ChatRoomEvent.TypingStarted -> {
//                    Toast.makeText(context,"${event.sender} 가 메세지를 입력 합니다.",Toast.LENGTH_SHORT).show()
                    typingUser.value = event.sender
                }
                is ChatRoomEvent.TypingStopped -> {
                    typingUser.value = null
                }
                is ChatRoomEvent.ChatRoomEnter -> {
                    playSentSound(SoundType.ENTER_ROOM)
//                    Toast.makeText(context,"${event.name} 가 입장하였습니다.",Toast.LENGTH_SHORT).show()
                }
                is ChatRoomEvent.ChatRoomLeave -> {
//                    Toast.makeText(context,"${event.name} 가 퇴장하였습니다.",Toast.LENGTH_SHORT).show()
                }
                is ChatRoomEvent.ScrollToBottom -> {
                    coroutineScope.launch {
                        if(messages.isNotEmpty()) {

                            listState.animateScrollToItem(messages.lastIndex)
                        }
                    }
                }
                is ChatRoomEvent.ClearInput -> {
                    inputMessage.value = ""
                    keyboardController?.hide()
                }
                is ChatRoomEvent.Kicked -> {
                    Toast.makeText(
                        navController.context,
                        "채팅방에서 추방 당했습니다. ㅠ.ㅠ",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.popBackStack()
                }
                is ChatRoomEvent.Exploded -> {
                    navController.previousBackStackEntry?.
                    savedStateHandle?.set("explodedRoomId",roomId)

                    navController.popBackStack()
                }
                else -> Unit
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("채팅방 #$roomId")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.back {
                                navController.popBackStack()
                            }
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            showSettingsPanel = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "설정"
                            )
                        }
                    }

                )
            },
            content = { padding ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(padding)
                        .navigationBarsPadding()
                ) {
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                            .padding(8.dp),
                        state = listState

                    ) {
                        items(messages) { message ->
                            ChatMessageItem(message, viewModel.me.name == message.sender.name)
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {

                        if (typingUser.value != null) {
                            Text(
                                text = "${typingUser.value}님이 입력중...",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }

                        OutlinedTextField(
                            value = inputMessage.value,
                            onValueChange = {
                                inputMessage.value = it
                                viewModel.onTypingChanged(it)
                                if (it.isBlank()) {
                                    viewModel.stopTyping()
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(24.dp),
                            placeholder = { Text("메세지 입력") },
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                if (inputMessage.value.isNotBlank()) {
                                    viewModel.sendMessage(inputMessage.value)
                                }
                            },
                            modifier = Modifier
                                .height(56.dp),
                            shape = RoundedCornerShape(24.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "전송")
                        }
                    }
                }
            }
        )

        AnimatedVisibility(
            visible = showSettingsPanel,
            enter = slideInHorizontally(initialOffsetX = { it}) + EnterTransition.None,
            exit = slideOutHorizontally(targetOffsetX = { it}) + ExitTransition.None,
            modifier = Modifier.fillMaxHeight()
                .width(LocalConfiguration.current.screenWidthDp.dp * 0.75f)
                .padding(WindowInsets.statusBars.asPaddingValues())
                .navigationBarsPadding()
                .align(Alignment.CenterEnd)
                .background(Color.LightGray)
                .zIndex(1f)
        ) {
            ChatRoomSettingContent(viewModel = viewModel,
                onClose = {showSettingsPanel = false},
                onKickUser = {target ->
                    kickTarget = target
                    showKickDialog = true
                },
                onLeaveRoom = {
                    showLeaveDialog = true
                },
                explodeRoom = {
                    showExplodDialog = true
                }
            )
        }

        if (explodedState) {
            ExplosionEffect(onExploded = {
                navController.previousBackStackEntry?.savedStateHandle?.set("explodedRoomId",roomId)

                navController.popBackStack()
            })
        }

    }
    if (showLeaveDialog) {
        AlertDialog(
            onDismissRequest = { showLeaveDialog = false},
            title = { Text("채팅방 나가기")},
            text = { Text("채팅방에서 나가시겠습니까?")},
            confirmButton = {
                TextButton(onClick = {
                    showLeaveDialog = false
                    viewModel.leaveRoom {
                        navController.popBackStack()
                    }
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showLeaveDialog = false
                }) {
                    Text("취소")
                }
            }
        )
    }

    if (showKickDialog && kickTarget != null) {
        AlertDialog(
            title = { Text("추방 하기")},
            text = { Text("${kickTarget?.name}님을 추방하시겠습니까?")},
            onDismissRequest = {
                showKickDialog = false
                kickTarget = null
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.kickUser(kickTarget!!) {
//                            showSettingsPanel = false
                        }
                        showKickDialog = false
                        kickTarget = null
                    }
                ) {
                    Text("추방")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showKickDialog = false
                        kickTarget = null
                    }
                ) {
                    Text("취소")
                }
            }
        )
    }

    if (showExplodDialog) {
        AlertDialog(
            onDismissRequest = { showExplodDialog = false},
            title = { Text("채팅방 폭파!!")},
            text = { Text("채팅방을 폭파 하시겠습니까?")},
            confirmButton = {
                TextButton(onClick = {
                    showExplodDialog = false
                    showSettingsPanel = false
                    // TODO
                    viewModel.triggerExplosion()
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showExplodDialog = false
                }) {
                    Text("취소")
                }
            }
        )
    }
}