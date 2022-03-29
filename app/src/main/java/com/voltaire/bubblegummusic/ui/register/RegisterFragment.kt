package com.voltaire.bubblegummusic.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.voltaire.bubblegummusic.R
import com.voltaire.bubblegummusic.databinding.RegisterFragmentBinding
import com.voltaire.bubblegummusic.models.User
import com.voltaire.bubblegummusic.ui.login.view.LoginActivity

class RegisterFragment : Fragment () {

    private lateinit var binding : RegisterFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHaveaccount.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegisterAccount.setOnClickListener {
            createUser()
        }
    }


    private fun createUser() {

        val uName = binding.txtUsername.text.toString()
        val uEmail = binding.txtEmail.text.toString()
        var uPassword = binding.txtPassword.text.toString()
        var uPasswordConfirm = binding.txtPasswordConfirm.text.toString()



        if (uName.isEmpty() || uEmail.isEmpty() || uEmail == null || uPassword.isEmpty() || uPassword == null || uPassword != uPasswordConfirm) {

            Toast.makeText(requireContext(), "Há algo de errado com os dados preenchidos.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.btnRegisterAccount.visibility = View.INVISIBLE
        binding.progressResgister.visibility = View.VISIBLE

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(uEmail,uPassword)
            .addOnSuccessListener {
                    saveUserFireBase()
                }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Falha na criação de conta.", Toast.LENGTH_SHORT).show()
                binding.btnRegisterAccount.visibility = View.VISIBLE
                binding.progressResgister.visibility = View.INVISIBLE
            }
    }

    private fun saveUserFireBase() {


        val uid = FirebaseAuth.getInstance().uid
        val email = binding.txtEmail.text.toString()
        val displayName = binding.txtUsername.text.toString()

        var newUser =  User (email ,uid, displayName)

        if (uid != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document (uid)
                .set(newUser)
                .addOnSuccessListener {
                    findNavController().navigate(R.id.action_RegisterFragment_to_WelcomeFragment)
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Teste na hora de salvar usuário", Toast.LENGTH_SHORT).show()
                }
        }
    }

}