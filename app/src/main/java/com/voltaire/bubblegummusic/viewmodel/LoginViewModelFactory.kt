package com.voltaire.bubblegummusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voltaire.bubblegummusic.repositories.LoginRepository


class LoginViewModelFactory(
    private val repository: LoginRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(repository) as T
    }
}