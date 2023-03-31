package com.example.mylibrary.net

/**
 * base基类所有类需要继承它
 */
data class BaseResponse<T>(
    val data: T,
    val ex: String? = "",
    val message: String? = "",
    val page: Int,
    val pageSize: Int,
    val returnCode: Int,
    val serialNo: String? = ""
) : java.io.Serializable {
    fun isOk(): Boolean {
        return returnCode == 200
    }
}