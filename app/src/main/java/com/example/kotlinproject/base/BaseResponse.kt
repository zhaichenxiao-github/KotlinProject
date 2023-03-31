package com.example.kotlinproject.base

data class BaseResponse<T>(
    val code: Int,
    val message: String = "",
    val data: T
) : java.io.Serializable