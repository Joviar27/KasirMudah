package com.cobasendiri.kasirmudah.ui.profile

import androidx.lifecycle.viewModelScope
import com.cobasendiri.kasirmudah.domain.model.ShopProfile
import com.cobasendiri.kasirmudah.domain.usecase.GetShopProfileUseCase
import com.cobasendiri.kasirmudah.domain.usecase.SaveShopProfileUseCase
import com.cobasendiri.kasirmudah.ui.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getShopProfileUseCase: GetShopProfileUseCase,
    private val saveShopProfileUseCase: SaveShopProfileUseCase
): BaseViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> get() = _state

    init {
        getShopProfile()
    }

    private fun getShopProfile(){
        viewModelScope.launch {
            getShopProfileUseCase.invoke().collect {
                it.handleResult{ shopProfile ->
                    _state.update { it.copy(shopProfile = shopProfile) }
                }
            }
        }
    }

    fun saveShopProfile(shopProfile: ShopProfile){
        viewModelScope.launch {
            saveShopProfileUseCase.invoke(shopProfile).handleResult{
                dismissEditProfileDialog()
            }
        }
    }

    fun showEditProfileDialog(shopProfile: ShopProfile){
        _state.update {
            it.copy(showEditProfileDialog = shopProfile)
        }
    }

    fun dismissEditProfileDialog(){
        _state.update {
            it.copy(showEditProfileDialog = null)
        }
    }

    fun showUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = true)
        }
    }

    fun dismissUnavailableDialog(){
        _state.update {
            it.copy(showUnavailableDialog = false)
        }
    }
}