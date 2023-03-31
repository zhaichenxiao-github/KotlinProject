package com.example.mylibrary.net

class ApiException(error: Error, e: Throwable?) : Exception() {
    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errorLog: String? //错误日志

    init {
        errCode = error.getKey()
        errorMsg = error.getValue()
        errorLog = e?.message
    }
}