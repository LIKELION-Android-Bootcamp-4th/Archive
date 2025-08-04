package com.likelion.liontalk

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class LionTalkApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"")

//        // 네이버는 반드시 application 레벨에서 초기화를 해줘야한다.
//        NaverIdLoginSDK.initialize(this,"naver_client_id","naver_client_secret","liontalk")
    }
}