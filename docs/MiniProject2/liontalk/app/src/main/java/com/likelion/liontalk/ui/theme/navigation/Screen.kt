package com.likelion.liontalk.ui.theme.navigation

sealed class Screen(val route: String) {
    object ChatRoomListScreen : Screen("chatroom_list")
    object ChatRoomScreen : Screen("chatroom_detail/{roomId}") {
        fun createRoute(roomId : Int) = "chatroom_detail/$roomId"
    }
    object SettingScreen : Screen("setting")
    object LauncherScreen : Screen("launcher")

    object SignScreen : Screen("sign")
}