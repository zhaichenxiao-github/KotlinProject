package com.example.kotlinproject

import android.accounts.NetworkErrorException
import android.widget.Toast
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExceptionUtil {
    fun cashException(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is HttpException -> {
                shortToast(e.message)
            }
            is SocketTimeoutException -> {
                shortToast(e.message)
            }
            is UnknownHostException, is NetworkErrorException -> {
                shortToast(e.message)
            }
            is ApiException -> {
                shortToast(e.msg)
            }
            else -> {

            }
        }
    }

    fun shortToast(msg: String?) {
        Toast.makeText(App.instance, msg, Toast.LENGTH_SHORT).show()
    }

}