package com.example.handmade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.handmade.data.database.AppDatabase
import com.example.handmade.data.repository.MainRepository
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.login_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val repo = MainRepository(db)

        val etEmailOrUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)

        view.findViewById<MaterialButton>(R.id.btnLogin).setOnClickListener {
            val email = etEmailOrUsername.text.toString().trim()
            val pass = etPassword.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val user = repo.login(email, pass)

                if (user != null) {
                    val options = NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()

                    findNavController().navigate(R.id.homeFragment, null, options)
                } else {
                    Toast.makeText(requireContext(), "Wrong email or password", Toast.LENGTH_SHORT).show()
                }
            }
        }

        view.findViewById<MaterialButton>(R.id.btnGoSignup).setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }
}
