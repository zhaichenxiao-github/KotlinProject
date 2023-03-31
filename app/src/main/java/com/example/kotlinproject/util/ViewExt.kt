package com.example.kotlinproject.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.example.kotlinproject.R

fun View.click(view: (View) -> Unit) {
    setOnClickListener {
        view.invoke(it)
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun View.inVisible() {
    visibility = View.INVISIBLE
}

@SuppressLint("StaticFieldLeak")
private var loadDialog: MaterialDialog? = null
fun AppCompatActivity.showDialog(msg: String? = "正在加载") {
    apply {
        if (!this.isFinishing) {
            if (loadDialog == null) {
                loadDialog = MaterialDialog(this)
                    .cancelOnTouchOutside(true)
                    .cancelable(true)
                    .cornerRadius(20f)
                    .customView(R.layout.layout_custom_progress_dialog_view)
                    .also {
                        it.findViewById<TextView>(R.id.loading_tips).text = msg
                    }
                    .lifecycleOwner(this)
            }
            loadDialog?.show()
        }
    }
}

fun Fragment.showDialog(msg: String? = "正在加载") {
    apply {
        if (loadDialog == null) {
            loadDialog = this.context?.let { it1 ->
                MaterialDialog(it1)
                    .cancelOnTouchOutside(true)
                    .cancelable(true)
                    .cornerRadius(20f)
                    .customView(R.layout.layout_custom_progress_dialog_view)
                    .also {
                        it.findViewById<TextView>(R.id.loading_tips).text = msg
                    }
                    .lifecycleOwner(this)
            }
        }
        loadDialog?.show()
    }
}

fun TextView.addTextChanged(
    beforeTextChanged: (String) -> Unit,
    onTextChanged: (String) -> Unit = {},
    afterTextChanged: (String) -> Unit = {}
) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            beforeTextChanged.invoke(p0.toString())
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}

fun Fragment.dismissDialog() {
    loadDialog?.dismiss()
    loadDialog = null
}

fun AppCompatActivity.dismissDialog() {
    loadDialog?.dismiss()
    loadDialog = null
}

fun MaterialDialog.cancelClick(view: (DialogInterface) -> Unit) {
    setOnCancelListener {
        view.invoke(it)
    }
}
