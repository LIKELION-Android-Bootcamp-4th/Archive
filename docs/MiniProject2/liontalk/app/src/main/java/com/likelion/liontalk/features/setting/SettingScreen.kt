package com.likelion.liontalk.features.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.likelion.liontalk.ui.theme.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavHostController) {

    val viewModel : SettingViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("사용자 설정") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment =  Alignment.CenterHorizontally
        ) {
            Text("사용자 정보를 입력해주세요" , style =MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = viewModel.userName,
                    onValueChange = { viewModel.userName = it},
                    label = { Text("사용자명")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = viewModel.avatarUrl,
                    onValueChange = { viewModel.avatarUrl = it},
                    label = { Text("아바타 이미지 url")},
                    modifier = Modifier.fillMaxWidth()
                )






            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (viewModel.userName.isNotBlank()) {
                        viewModel.saveProfile()

                        navController.navigate(Screen.ChatRoomListScreen.route) {
                            popUpTo(Screen.SettingScreen.route) {inclusive = true}
                        }
                    }
                }
            ) {
                Text("저장하기")
            }
        }

    }
}