package com.psw9999.firebase_dynamiclink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.psw9999.firebase_dynamiclink.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTestBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.tvTest.text = intent.getStringExtra("NUMBER")
    }
}