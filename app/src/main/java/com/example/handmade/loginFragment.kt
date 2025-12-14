package com.example.handmade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)

        val repo = MainRepository(AppDatabase.getInstance(requireContext()))

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "please enter username & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val user = repo.getUserByName(username)

                if (user == null) {
                    Toast.makeText(requireContext(), "wrong username", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (user.password != password) {
                    Toast.makeText(requireContext(), "wrong password", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                findNavController().navigate(R.id.homeFragment)
            }
        }

        // Sign up (لو موجود)
        view.findViewById<View?>(R.id.tvGoSignup)?.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }
}