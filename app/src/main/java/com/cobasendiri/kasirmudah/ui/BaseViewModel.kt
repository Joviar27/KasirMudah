package com.cobasendiri.kasirmudah.ui

import androidx.lifecycle.ViewModel
import com.cobasendiri.kasirmudah.domain.Result
import com.cobasendiri.kasirmudah.ui.uimessage.UiMessage
import com.cobasendiri.kasirmudah.ui.utils.ErrorMessageMapper.asUiMessage

abstract class BaseViewModel: ViewModel() {

    protected fun <T> Result<T>.handleResult(
        onSuccess: ((T) -> Unit)? = null
    ){
        when(this){
            is Result.Success -> onSuccess?.invoke(this.data)
            is Result.Error -> {
                val uiMessage = this.error.asUiMessage()
                showUiMessage(uiMessage)
            }
        }
    }

    open fun showUiMessage(message: UiMessage){}

    open fun uiMessageShown(){}
}