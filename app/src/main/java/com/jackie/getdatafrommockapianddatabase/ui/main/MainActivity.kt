package com.jackie.getdatafrommockapianddatabase.ui.main

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jackie.getdatafrommockapianddatabase.R
import com.jackie.getdatafrommockapianddatabase.ui.base.BaseActivity
import com.jackie.getdatafrommockapianddatabase.utils.fromHtml
import com.jackie.getdatafrommockapianddatabase.utils.setInvisible
import com.jackie.getdatafrommockapianddatabase.utils.setVisible
import com.jackie.getdatafrommockapianddatabase.utils.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        btnLoadData.setOnClickListener {
            viewModel.loadData()
        }
        btnClearDb.setOnClickListener {
            viewModel.clearDb()
        }
        btnCancel.setOnClickListener {
            viewModel.cancel()
        }
    }

    override fun initObserver() {
        viewModel.uiState.observe(this, Observer {
            render(it)
        })
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                onLoad(uiState)
            }
            is UiState.Success -> {
                onSuccess(uiState)
            }
            is UiState.Error -> {
                onError(uiState)
            }
        }
    }

    private fun onLoad(loading: UiState.Loading) {
        when (loading) {
            UiState.Loading.DbLoading -> {
                pbLoadDataFromDb.setVisible()
                ivLoadDataFromDb.setInvisible()
                ivLoadDataFromNetwork.setInvisible()
            }
            UiState.Loading.NetworkLoading -> {
                pbLoadDataFromNetwork.setVisible()
            }
        }
    }

    private fun onSuccess(success: UiState.Success) {
        when (success.dataSource) {
            DataSource.DB -> {
                pbLoadDataFromDb.setInvisible()
                ivLoadDataFromDb.setVisible()
                ivLoadDataFromDb.setImageDrawable(getDrawable(R.drawable.ic_check_green_24dp))
            }
            DataSource.NETWORK -> {
                pbLoadDataFromNetwork.setInvisible()
                ivLoadDataFromNetwork.setVisible()
                ivLoadDataFromNetwork.setImageDrawable(getDrawable(R.drawable.ic_check_green_24dp))
            }
        }
        val readableVersions = success.recentVersions.map { "API ${it.apiLevel}: ${it.name}" }
        tvResult.text = fromHtml(
            "<b>Recent Android Versions [from ${success.dataSource.title}]</b><br>${
                readableVersions.joinToString(
                    separator = "<br>"
                )
            }"
        )
    }

    private fun onError(error: UiState.Error) {
        when (error.dataSource) {
            DataSource.DB -> {
                pbLoadDataFromDb.setInvisible()
                ivLoadDataFromDb.setVisible()
                ivLoadDataFromDb.setImageDrawable(getDrawable(R.drawable.ic_clear_red_24dp))
            }
            DataSource.NETWORK -> {
                pbLoadDataFromNetwork.setInvisible()
                ivLoadDataFromNetwork.setVisible()
                ivLoadDataFromNetwork.setImageDrawable(getDrawable(R.drawable.ic_clear_red_24dp))
            }
        }
        toast(error.message)
    }
}