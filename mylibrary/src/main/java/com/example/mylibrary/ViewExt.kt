package com.example.mylibrary

import android.app.Activity
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.mylibrary.base.BaseViewModel
import com.example.mylibrary.net.ApiException
import com.example.mylibrary.net.BaseResponse
import com.example.mylibrary.net.ExceptionUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Double.parseDouble
import java.lang.reflect.ParameterizedType

fun <VM> Fragment.getViewModel(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}

fun <VM> Activity.getViewModel(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as VM
}

fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success: (T) -> Unit,
    error: (ApiException) -> Unit = {},
    loadingDialog: Boolean = true,
): Job {
    return viewModelScope.launch {
        kotlin.runCatching {
            if (loadingDialog)
                loadDialog.postValue(true)
            //请求体
            block.invoke()
        }.onSuccess {
            //请求成功关闭弹框
            loadDialog.postValue(false)
            kotlin.runCatching {
                if (it.isOk())
                    success.invoke(it.data)
            }.onFailure {
                error.invoke(ExceptionUtil.catchException(it))
            }
        }.onFailure {
            //请求异常关闭弹框
            loadDialog.postValue(false)
            error.invoke(ExceptionUtil.catchException(it))
        }
    }
}

private var loadingDialog: MaterialDialog? = null
fun AppCompatActivity.showDialog(msg: String? = "正在请求中") {
    let {
        if (loadingDialog == null) {
            loadingDialog = MaterialDialog(this)
                .cancelable(false)
                .cancelOnTouchOutside(false)
                .lifecycleOwner(this)
        }
        loadingDialog?.show()
    }
}

fun AppCompatActivity.dismissDialog() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

fun Fragment.showDialog() {
    let {
        if (loadingDialog == null) {
            loadingDialog = MaterialDialog(this.requireContext())
                .cancelable(false)
                .cancelOnTouchOutside(false)
                .lifecycleOwner(this)
        }
        loadingDialog?.show()
    }
}

fun Fragment.dismissDialog() {
    loadingDialog?.dismiss()
    loadingDialog = null
}

fun convertRationalLatLonToFloat(rationalString: String?, ref: String?): Number {
    return if (TextUtils.isEmpty(rationalString) || TextUtils.isEmpty(ref)) {
        0
    } else try {
        val parts = rationalString?.split(",".toRegex())!!.toTypedArray()
        var pair: Array<String>
        pair = parts[0].split("/".toRegex()).toTypedArray()
        val degrees = (parseDouble(pair[0].trim { it <= ' ' }, 0.0)
                / parseDouble(pair[1].trim { it <= ' ' }, 1.0))
        pair = parts[1].split("/".toRegex()).toTypedArray()
        val minutes = (parseDouble(pair[0].trim { it <= ' ' }, 0.0)
                / parseDouble(pair[1].trim { it <= ' ' }, 1.0))
        pair = parts[2].split("/".toRegex()).toTypedArray()
        val seconds = (parseDouble(pair[0].trim { it <= ' ' }, 0.0)
                / parseDouble(pair[1].trim { it <= ' ' }, 1.0))
        val result = degrees + minutes / 60.0 + seconds / 3600.0
        if ("S" == ref || "W" == ref) {
            (-result).toFloat()
        } else result.toFloat()
    } catch (e: NumberFormatException) {
        0
    } catch (e: ArrayIndexOutOfBoundsException) {
        0
    } catch (e: Throwable) {
        0
    }
}