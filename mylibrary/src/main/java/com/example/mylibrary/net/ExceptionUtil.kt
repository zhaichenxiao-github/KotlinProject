package com.example.mylibrary.net

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException

object ExceptionUtil {
    fun catchException(e: Throwable): ApiException {
        val ext: ApiException
        when (e) {
            is HttpException -> {
                ext = ApiException(Error.NETWORK_ERROR, e)
                return ext
            }
            is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                ext = ApiException(Error.PARSE_ERROR, e)
                return ext
            }
            is ConnectException -> {
                ext = ApiException(Error.NETWORK_ERROR, e)
                return ext
            }
            is SSLException -> {
                ext = ApiException(Error.SSL_ERROR, e)
                return ext
            }
            is ConnectTimeoutException -> {
                ext = ApiException(Error.TIMEOUT_ERROR, e)
                return ext
            }
            is SocketTimeoutException -> {
                ext = ApiException(Error.TIMEOUT_ERROR, e)
                return ext
            }
            is UnknownHostException -> {
                ext = ApiException(Error.TIMEOUT_ERROR, e)
                return ext
            }
            is ApiException -> return e

            else -> {
                ext = ApiException(Error.UNKNOWN, e)
                return ext
            }

        }
        ext = ApiException(Error.UNKNOWN, e)
        return ext
    }
}