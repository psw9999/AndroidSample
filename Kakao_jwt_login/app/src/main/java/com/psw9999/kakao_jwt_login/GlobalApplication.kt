package com.psw9999.kakao_jwt_login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.psw9999.kakao_jwt_login.BuildConfig.KAKAO_NATIVE_KEY

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, KAKAO_NATIVE_KEY)
    }
}