package com.example.mylibrary

import com.kunminx.architecture.ui.callback.UnPeekLiveData

//防止页面销毁造成livedata数据倒灌问题
class EventLiveData<T> : UnPeekLiveData<T>()