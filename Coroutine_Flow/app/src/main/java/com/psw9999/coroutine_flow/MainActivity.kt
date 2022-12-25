package com.psw9999.coroutine_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val testFlow = MutableStateFlow<Boolean>(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            collectData()
        }
        observeData()
        setContentView(R.layout.activity_main)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                testFlow.collectLatest {
                    println("testFlow : $testFlow")
                }
            }
        }
    }

    private suspend fun collectData() {
        try {
            occurError().collect {
                testFlow.emit(it)
            }
        } catch (e: Exception) {
            println("error")
        }
    }

    // 첫번째 에러 발생
    private suspend fun occurError(): Flow<Boolean> =
        flow {
            throw Exception()
            emit(true)
        }
}