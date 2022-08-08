package com.voltaire.bubblegummusic.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.voltaire.bubblegummusic.repositories.LoginRepository

class RegisterViewModel(
    private val repository: LoginRepository,
    private val userLiveData: MutableLiveData<FirebaseUser?> = repository.getUserLiveData(),
    private val loggedOutLiveData: MutableLiveData<Boolean>? = repository.getLoggedOutLiveData()
    ) : ViewModel()
{

    fun register(email: String, password: String, name: String )  = repository.register(email, password, name)

    fun logOut() { repository.logOut() }

    fun getUserLiveData() = userLiveData

    fun getLoggedOutLiveData() = loggedOutLiveData

}