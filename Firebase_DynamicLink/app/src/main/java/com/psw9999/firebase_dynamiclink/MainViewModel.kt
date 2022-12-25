package com.psw9999.firebase_dynamiclink

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _shareEvent =
        MutableSharedFlow<Boolean>()

    @OptIn(FlowPreview::class)
    val shareEvent = _shareEvent.debounce(500)

    fun onBtnClick() {
        viewModelScope.launch {
            _shareEvent.emit(true)
        }
    }
}