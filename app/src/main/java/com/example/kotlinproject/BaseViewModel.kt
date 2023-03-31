package com.example.kotlinproject

import androidx.lifecycle.*
import com.kunminx.architecture.domain.message.MutableResult
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

open class BaseViewModel : ViewModel() {
    val showLoading by lazy { UnPeekLiveData<String>() }
    val dismissLoading by lazy { UnPeekLiveData<Boolean>() }
}