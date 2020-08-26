package com.jackie.getdatafrommockapianddatabase.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableLiveData<T>()
    val uiState = _uiState
}