package com.example.mylibrary.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.dismissDialog
import com.example.mylibrary.getViewModel
import com.example.mylibrary.showDialog
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        setContentView(mBinding.root)
        mViewModel = createViewModel()
        initView(savedInstanceState)
        registerUIChanged()
    }

    private fun registerUIChanged() {
        mViewModel.loadDialog.observe(this) {
            if (it) {
                showDialog()
            } else {
                dismissDialog()
            }
        }
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getViewModel(this)]
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getLayoutId(): Int
}