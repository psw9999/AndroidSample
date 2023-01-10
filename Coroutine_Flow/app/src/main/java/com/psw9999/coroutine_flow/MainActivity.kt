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
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.testButton.setOnClickListener {
            println("Click")
            if (job == null) {
                job = CoroutineScope(Dispatchers.Default).launch {
                    println("Job Start!")
                    testFlow.collect{
                        println("testFlow")
                    }
                    delay(2000)
                    println("Job End!")
                    job = null
                }
            }
        }
    }

    private val testFlow = flow {
        throw(Exception())
        emit(true)
    }.catch {
        println("Error Occur!")
    }

}