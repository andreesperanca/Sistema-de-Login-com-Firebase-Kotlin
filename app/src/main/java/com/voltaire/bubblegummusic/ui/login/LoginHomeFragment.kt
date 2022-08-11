package com.voltaire.bubblegummusic.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.voltaire.bubblegummusic.R
import com.voltaire.bubblegummusic.databinding.FragmentLoginHomeBinding
import com.voltaire.bubblegummusic.ui.main.MainActivity
import com.voltaire.bubblegummusic.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginHomeFragment : Fragment() {

    private lateinit var binding: FragmentLoginHomeBinding
    private val viewModel: LoginViewModel by viewModel()

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.action_loginHomeFragment_to_registerFragment)
        }

        binding.btnSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_loginHomeFragment_to_loginFragment)
        }

    }
}