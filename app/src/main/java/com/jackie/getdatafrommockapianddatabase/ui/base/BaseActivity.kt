package com.jackie.getdatafrommockapianddatabase.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initData()
        initView()
        initObserver()
    }

    abstract fun getLayoutId(): Int

    protected open fun initData() {}

    protected open fun initView() {}

    protected open fun initObserver() {}
}