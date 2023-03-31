package com.example.kotlinproject

import androidx.lifecycle.MutableLiveData
import com.example.kotlinproject.util.request

class MainViewModel : BaseViewModel() {
    private val api by lazy { RetrofitManager.getInstance(ApiService::class.java) }
    private val liveData by lazy { MutableLiveData<Bean>() }
    fun getData() {
        request({ api.getData() }, {
            liveData.postValue(it)
        })
    }
}