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
import com.example.handmade.data.entities.UserEntity
import com.example.handmade.data.repository.MainRepository
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.signup_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getInstance(requireContext())
        val repo = MainRepository(db)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirm = view.findViewById<EditText>(R.id.etConfirm)

        view.findViewById<MaterialButton>(R.id.btnCreate).setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val pass = etPassword.text.toString()
            val confirm = etConfirm.text.toString()

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirm) {
                Toast.makeText(requireContext(), "Passwords not matching", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                // لو انت ضفت signup() في MainRepository استخدمها (أفضل عشان يمنع تكرار الإيميل)
                val ok = try {
                    repo.signup(UserEntity(name = name, email = email, password = pass))
                } catch (e: Exception) {
                    false
                }

                if (ok) {
                    Toast.makeText(requireContext(), "Account created", Toast.LENGTH_SHORT).show()
                    // بعد التسجيل نرجّع المستخدم للّوجين
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Email already exists (or error)", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
