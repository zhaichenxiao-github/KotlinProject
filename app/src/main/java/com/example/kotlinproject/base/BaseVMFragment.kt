package com.example.kotlinproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kotlinproject.BaseViewModel
import com.example.kotlinproject.util.dismissDialog
import com.example.kotlinproject.util.getVmClazz
import com.example.kotlinproject.util.inflateBindingWithGeneric
import com.example.kotlinproject.util.showDialog

abstract class BaseVMFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    lateinit var mBinding: T
    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity
    var isFirst: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflateBindingWithGeneric(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mActivity = activity as AppCompatActivity
        mViewModel = createViewModel()
        registerUIchanged()
        initView(savedInstanceState)
    }

    private fun registerUIchanged() {
        mViewModel.showLoading.observe(this) {
            showDialog(it)
        }
        mViewModel.dismissLoading.observe(this) {
            dismissDialog()
        }
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    abstract fun initView(savedInstanceState: Bundle?)
}