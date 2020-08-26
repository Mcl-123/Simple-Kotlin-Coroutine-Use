package com.jackie.getdatafrommockapianddatabase.ui.main

import androidx.lifecycle.viewModelScope
import com.jackie.getdatafrommockapianddatabase.Repository
import com.jackie.getdatafrommockapianddatabase.ui.base.BaseViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<UiState>() {

    private var job: Job? = null

    fun loadData() {
        job = viewModelScope.launch {
            _uiState.value = UiState.Loading.DbLoading
            val loadDataFromDb = Repository.loadDataFromDb()
            if (loadDataFromDb.isEmpty()) {
                _uiState.value = UiState.Error(DataSource.DB, "db empty")
            } else {
                _uiState.value = UiState.Success(DataSource.DB, loadDataFromDb)
            }
            _uiState.value = UiState.Loading.NetworkLoading
            try {
                val loadDataFromNetwork = Repository.loadDataFromNetwork()
                _uiState.value = UiState.Success(DataSource.NETWORK, loadDataFromNetwork)
            } catch (cancellationException: CancellationException) {
                _uiState.value = UiState.Error(DataSource.NETWORK, "network cancelled")
            } catch (exception: Exception) {
                exception.printStackTrace()
                _uiState.value = UiState.Error(DataSource.NETWORK, "network error")
            }
        }
    }

    fun clearDb() {
        viewModelScope.launch {
            Repository.clearDb()
        }
    }

    fun cancel() {
        job?.cancel()
    }
}