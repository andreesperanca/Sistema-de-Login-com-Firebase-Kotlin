package com.voltaire.bubblegummusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.voltaire.bubblegummusic.databinding.FragmentRegisterBinding
import com.voltaire.bubblegummusic.repositories.LoginRepository
import com.voltaire.bubblegummusic.ui.main.MainActivity
import com.voltaire.bubblegummusic.viewmodel.LoginViewModel
import com.voltaire.bubblegummusic.viewmodel.LoginViewModelFactory
import com.voltaire.bubblegummusic.viewmodel.RegisterViewModel
import com.voltaire.bubblegummusic.viewmodel.RegisterViewModelFactory

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val auth = FirebaseAuth.getInstance()
    private val store = FirebaseFirestore.getInstance()
    private val userLiveData = MutableLiveData<FirebaseUser?>()
    private val loggedOutLiveData = MutableLiveData<Boolean>()

    private val viewModel by lazy {
        val repository =
            LoginRepository(
                activity?.application,
                firebaseAuth = auth,
                userLiveData = userLiveData,
                loggedOutLiveData = loggedOutLiveData,
                firebaseStore = store
            )
        val factory = RegisterViewModelFactory(repository)
        val provider = ViewModelProvider(this, factory)
        provider[RegisterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.getUserLiveData().observe(this) {
            if (it != null) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnRegister.setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        val name = binding.tilName.editText?.text.toString()
        val email = binding.tilEmail.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()

        binding.btnRegister.visibility = View.VISIBLE
        viewModel.register(email, password, name)
        binding.progressBar.visibility = View.INVISIBLE

    }
}
