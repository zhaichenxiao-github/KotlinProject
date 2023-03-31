package com.example.kotlinproject

import android.provider.ContactsContract.Data
import com.example.kotlinproject.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.POST

interface ApiService {
    @POST
    suspend fun getData(): BaseResponse<Bean>
}