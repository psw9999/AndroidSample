package com.psw9999.coroutine_flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.psw9999.coroutine_flow.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val stateFlow = MutableStateFlow(false)
    val job = launchNewJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.testButton.setOnClickListener {
            if (!job.isActive) {
                println("Job Start")
                job.start()
            }
            else println("이미 실행중입니다.")
        }
    }

    fun launchNewJob() = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
        delay(1000)
        println("job End")
    }

    private val testFlow =
        flow {
            println("job Start")
            delay(1000)
            emit(true)
        }

}