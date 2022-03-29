package com.voltaire.bubblegummusic.ui.login.view

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.voltaire.bubblegummusic.databinding.ActivityLoginBinding
import com.voltaire.bubblegummusic.ui.main.MainActivity
import com.voltaire.bubblegummusic.ui.register.RegisterActivity
import com.voltaire.bubblegummusic.common.view.LoadingButton

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {


            var uEmail = binding.editEmail.text.toString()
            var uPassword = binding.editPassword.text.toString()

            if (uEmail.isEmpty() || uEmail == null || uPassword.isEmpty() || uPassword == null) {

                Toast.makeText(this, "Preencha os campos.", Toast.LENGTH_LONG).show()

                return@setOnClickListener
            }

            binding.btnLogin.visibility = View.INVISIBLE
            binding.progressLogin.visibility = View.VISIBLE



            FirebaseAuth.getInstance().signInWithEmailAndPassword(uEmail, uPassword)

                .addOnSuccessListener {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags =
                        (Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Crie uma conta antes de fazer login.", Toast.LENGTH_SHORT)
                        .show()
                    binding.btnLogin.visibility = View.VISIBLE
                    binding.progressLogin.visibility = View.INVISIBLE
                }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
