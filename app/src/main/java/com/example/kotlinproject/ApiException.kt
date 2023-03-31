package com.example.kotlinproject

class ApiException(
    val msg: String? = "",
    val code: Int
) : Throwable() {
    override fun toString(): String {
        return "ApiException(msg=$msg, code=$code)"
    }
}