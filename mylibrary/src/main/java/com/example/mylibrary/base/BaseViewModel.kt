package com.example.mylibrary.base

import androidx.lifecycle.ViewModel
import com.example.mylibrary.EventLiveData

open class BaseViewModel : ViewModel() {
    val loadDialog by lazy { EventLiveData<Boolean>() }
}