package com.likelion.liontalk.features.sign

import android.app.Application
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.likelion.liontalk.data.remote.datasource.FirebaseAuthDataSource
import com.likelion.liontalk.data.remote.datasource.KakaoDataSource
import com.likelion.liontalk.data.remote.datasource.NaverDataSource
import com.likelion.liontalk.data.repository.AuthRepository
import com.likelion.liontalk.features.chatroomlist.ChatRoomListViewModel

@Composable
fun SignScreen(navController: NavController) {

    val context = LocalContext.current
    val application = context.applicationContext as Application

    val authRepository = remember {
        AuthRepository(
            FirebaseAuthDataSource(),
            KakaoDataSource(context),
            NaverDataSource(context)
        )
    }

    val viewModel: SignViewModel = viewModel(
        factory = SignViewModelFactory(application, authRepository)
    )

    val googleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { intent -> viewModel.handleGoogleSignInResult(intent,navController) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { viewModel.kakaoLogin(navController) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("카카오 로그인")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.naverLogin(navController) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("네이버 로그인")
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.googleLogin(context, googleLauncher) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("구글 로그인")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun SignScreenPreview() {
//    // Dummy ViewModel (로그인 함수는 비워둠)
//    val dummyViewModel = object : SignViewModel() {
//        override fun kakaoLogin(context: android.content.Context) { }
//        override fun naverLogin(context: android.content.Context) { }
//        // googleLogin은 Preview에서는 필요 없음
//    }
//
//    SignScreen(viewModel = dummyViewModel)
//}

