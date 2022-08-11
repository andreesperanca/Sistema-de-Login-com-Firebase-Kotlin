package com.voltaire.bubblegummusic.ui.login

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
import com.voltaire.bubblegummusic.R
import com.voltaire.bubblegummusic.databinding.FragmentRegisterBinding
import com.voltaire.bubblegummusic.repositories.LoginRepository
import com.voltaire.bubblegummusic.ui.main.MainActivity
import com.voltaire.bubblegummusic.viewmodel.LoginViewModel
import com.voltaire.bubblegummusic.viewmodel.RegisterViewModel
import com.voltaire.bubblegummusic.viewmodel.RegisterViewModelFactory
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModel()

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

        binding.tvHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener { createUser() }

        binding.btnClose.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun createUser() {
        binding.btnRegister.isEnabled = true
        val name = binding.tilName.editText?.text.toString()
        val email = binding.tilEmail.editText?.text.toString()
        val password = binding.tilPassword.editText?.text.toString()
        binding.progressBar.visibility = View.VISIBLE
        viewModel.register(email, password, name, updateUI = {
            binding.progressBar.visibility = View.INVISIBLE
        })
        binding.btnRegister.isEnabled = false
    }

}
