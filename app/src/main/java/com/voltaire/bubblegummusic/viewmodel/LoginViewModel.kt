package com.voltaire.bubblegummusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.voltaire.bubblegummusic.repositories.LoginRepository

class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel()
{

    fun login (email: String, password: String) = repository.login(email, password)

    fun register(email: String, password: String, name: String) = repository.register(email, password, name)

    fun getUserLiveData() = repository.getUserLiveData()

}