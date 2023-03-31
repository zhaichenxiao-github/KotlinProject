package com.example.kotlinproject

class Retrotry {
    private val api by lazy { RetrofitManager.getInstance(ApiService::class.java) }

    suspend fun getDataInfo(){
        api.getData()
    }
}