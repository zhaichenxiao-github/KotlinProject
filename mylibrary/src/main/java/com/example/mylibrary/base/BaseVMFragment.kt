package com.example.mylibrary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mylibrary.dismissDialog
import com.example.mylibrary.getViewModel
import com.example.mylibrary.showDialog

abstract class BaseVMFragment<T : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    lateinit var mBinding: T
    lateinit var mViewModel: VM
    lateinit var mActivity: AppCompatActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = activity as AppCompatActivity
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