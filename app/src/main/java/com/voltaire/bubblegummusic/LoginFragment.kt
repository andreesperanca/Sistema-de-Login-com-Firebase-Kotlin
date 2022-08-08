package com.voltaire.bubblegummusic

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.voltaire.bubblegummusic.databinding.FragmentLoginBinding
import com.voltaire.bubblegummusic.repositories.LoginRepository
import com.voltaire.bubblegummusic.ui.main.MainActivity
import com.voltaire.bubblegummusic.util.checkTextView
import com.voltaire.bubblegummusic.util.toastCreator
import com.voltaire.bubblegummusic.viewmodel.LoginViewModel
import com.voltaire.bubblegummusic.viewmodel.LoginViewModelFactory


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
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
        val factory = LoginViewModelFactory(repository)
        val provider = ViewModelProvider(this, factory)
        provider[LoginViewModel::class.java]
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
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.btnLogin.setOnClickListener {
            val userEmail = binding.inputEmail.editText?.text.toString()
            val userPassword = binding.inputPassword.editText?.text.toString()

            if (checkTextView(userEmail, userPassword)) {
                viewModel.login(userEmail, userPassword)
            } else {
                toastCreator(
                    requireContext(),
                    message = "Os campos devem ser preenchidos corretamente."
                )
            }

        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}