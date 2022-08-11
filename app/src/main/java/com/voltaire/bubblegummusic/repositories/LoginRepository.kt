package com.voltaire.bubblegummusic.repositories

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.voltaire.bubblegummusic.models.User
import com.voltaire.bubblegummusic.ui.main.MainActivity

class LoginRepository(
    private val application: Application?,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStore: FirebaseFirestore,
    private val userLiveData: MutableLiveData<FirebaseUser?>,
    private val loggedOutLiveData: MutableLiveData<Boolean>
    )
{

    fun login(email: String, password: String, updateUI: ()-> Unit = {}) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    userLiveData.postValue(firebaseAuth.currentUser)
                    updateUI()
                } else {
                    Toast.makeText(application?.applicationContext, it.exception?.message, Toast.LENGTH_LONG).show()
                    updateUI()
                }
            }
    }

    fun register(email: String, password: String, name: String, updateUI: ()-> Unit = {}) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    saveUserFireBase(email, firebaseAuth.uid.toString(), name)
                    updateUI()
                } else {
                    Toast.makeText(
                        application?.applicationContext,
                        "Registration Failure: " + it.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI()
                }
            }


    }

    private fun saveUserFireBase(email: String, uid: String, name: String) {

        val newUser = User(email, uid, name)

        firebaseStore.collection("users")
            .document(uid)
            .set(newUser)
            .addOnCompleteListener {
                userLiveData.value = firebaseAuth.currentUser
            }
            .addOnFailureListener {
                Toast.makeText(
                    application,
                    it.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser?> {
        userLiveData.value = FirebaseAuth.getInstance().currentUser
        return userLiveData
    }

    fun getLoggedOutLiveData() : MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

}