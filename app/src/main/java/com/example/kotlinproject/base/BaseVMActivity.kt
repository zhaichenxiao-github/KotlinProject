package com.example.kotlinproject.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinproject.BaseViewModel
import com.example.kotlinproject.util.dismissDialog
import com.example.kotlinproject.util.getVmClazz
import com.example.kotlinproject.util.inflateBindingWithGeneric
import com.example.kotlinproject.util.showDialog
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<T : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = inflateBindingWithGeneric(layoutInflater)
        setContentView(mBinding.root)
        mViewModel = createViewModel()
        registerUiChange()
        initView()
    }

    private fun registerUiChange() {
        mViewModel.showLoading.observe(this) {
            showLoadView(it)
        }
        mViewModel.dismissLoading.observe(this) {
            dismissLoadView()
        }
    }

    private fun dismissLoadView() {
        dismissDialog()
    }

    private fun showLoadView(it: String?) {
        showDialog(it)
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }

    abstract fun initView()

}