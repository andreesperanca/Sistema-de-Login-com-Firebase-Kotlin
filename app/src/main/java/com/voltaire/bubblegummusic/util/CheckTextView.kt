package com.voltaire.bubblegummusic.util

import android.content.Context
import android.widget.Toast

fun checkTextView(email: String, password: String): Boolean {
    return email.isNotEmpty() && email.isNotBlank() &&
            password.isNotEmpty() && email.isNotBlank()
}

fun toastCreator(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}