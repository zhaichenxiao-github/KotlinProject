package com.example.mylibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mylibrary.base.BaseVMActivity
import com.example.mylibrary.base.BaseViewModel
import com.example.mylibrary.databinding.ActivityMainBinding

class MainActivity : BaseVMActivity<ActivityMainBinding, BaseViewModel>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }
}