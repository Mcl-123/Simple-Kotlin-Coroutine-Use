package com.jackie.getdatafrommockapianddatabase.ui.main

import com.jackie.getdatafrommockapianddatabase.logic.model.AndroidVersion

sealed class UiState {
    open class Loading : UiState() {
        object DbLoading : Loading()
        object NetworkLoading : Loading()
    }

    class Success(val dataSource: DataSource, val recentVersions: List<AndroidVersion>) : UiState()
    class Error(val dataSource: DataSource, val message: String) : UiState()
}

enum class DataSource(val title: String) {
    DB("数据库"),
    NETWORK("网络")
}