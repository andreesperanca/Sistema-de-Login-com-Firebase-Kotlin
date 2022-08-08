package com.voltaire.bubblegummusic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.voltaire.bubblegummusic.repositories.LoginRepository

class RegisterViewModelFactory(
    private val repository: LoginRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegisterViewModel(repository) as T
    }
}