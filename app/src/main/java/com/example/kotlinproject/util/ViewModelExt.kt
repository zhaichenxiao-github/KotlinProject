package com.example.kotlinproject.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinproject.ApiException
import com.example.kotlinproject.BaseViewModel
import com.example.kotlinproject.Bean
import com.example.kotlinproject.ExceptionUtil
import com.example.kotlinproject.base.BaseResponse
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    showLoad: Boolean = false,
    loadMsg: String = "正在加载"
): Job {
    return viewModelScope.launch {
        kotlin.runCatching {
            if (showLoad) {
                showLoading.postValue(loadMsg)
            }
            block()
        }.onSuccess {
            //关闭弹框
            dismissLoading.postValue(true)
            kotlin.runCatching {
//                if (it == 200) {
//                }
                //判断请求码是否正确
                success.invoke(it.data)
            }.onFailure {
                it.printStackTrace()
                ExceptionUtil.cashException(ApiException(it.message, -1))
            }
        }.onFailure {
            it.message
            it.printStackTrace()
            ExceptionUtil.cashException(it)
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}