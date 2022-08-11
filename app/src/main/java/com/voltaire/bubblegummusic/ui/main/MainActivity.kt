package com.voltaire.bubblegummusic.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.voltaire.bubblegummusic.R
import com.voltaire.bubblegummusic.databinding.ActivityMainBinding
import com.voltaire.bubblegummusic.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_BubbleGumMusic)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.email
            binding.txtMain.text = getString(R.string.logged, name)
            binding.btnLoggout.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                user.let {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}