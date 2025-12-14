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

        // عدلي الـ IDs دول لو أسماء الحقول عندك مختلفة في login_page.xml
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<MaterialButton>(R.id.btnLogin)

        val db = AppDatabase.getInstance(requireContext())
        val repo = MainRepository(db)

        btnLogin.setOnClickListener {

            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // 1) Validation
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "اكتبي اليوزر نيم والباسورد", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2) Check from DB
            viewLifecycleOwner.lifecycleScope.launch {

                // ✅ لازم يكون عندك دالة زي دي في repo
                val user = repo.getUserByName(username)

                if (user == null) {
                    Toast.makeText(requireContext(), "اليوزر نيم غلط / مش موجود", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                if (user.password != password) {
                    Toast.makeText(requireContext(), "الباسورد غلط", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // 3) Success
                findNavController().navigate(R.id.homeFragment)
            }
        }

        view.findViewById<View>(R.id.tvGoSignup)?.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }
}
