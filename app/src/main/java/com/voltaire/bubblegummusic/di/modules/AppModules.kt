package com.voltaire.bubblegummusic.di.modules

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.voltaire.bubblegummusic.repositories.LoginRepository
import com.voltaire.bubblegummusic.viewmodel.LoginViewModel
import com.voltaire.bubblegummusic.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModules = module {

    single <LoginRepository> {
        val loggedOutLiveData = MutableLiveData<Boolean>()
        val userLiveData = MutableLiveData<FirebaseUser?>()
        LoginRepository(
            application = get (),
            firebaseAuth = get (),
            firebaseStore = get(),
            userLiveData = userLiveData,
            loggedOutLiveData = loggedOutLiveData
            )
    }

    single <FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single <FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    viewModel <LoginViewModel> {
         LoginViewModel( get () )
     }

    viewModel<RegisterViewModel> {
        RegisterViewModel(get ())
    }

}