package com.psw9999.firebase_dynamiclink

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        initDynamicLink()
    }

    private fun initDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { dynamicLinkData ->
                dynamicLinkData?.let { uri ->
                    val deepLink = uri.link.toString()
                    val stockInfoNumber =
                        deepLink.substring(deepLink.lastIndexOf("/") + 1)
                    val activityIntent = Intent(this, TestActivity::class.java).apply {
                        putExtra("NUMBER", stockInfoNumber)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(activityIntent)
                }
            }
            .addOnFailureListener {
            }
    }
}