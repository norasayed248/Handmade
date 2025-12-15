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

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "empty username & pass", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {
                val user = repo.login(username, password)

                if (user == null) {
                    Toast.makeText(requireContext(), "wrong username or pass", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // ✅ هنا الإضافة المطلوبة (من غير ما نبوّظ حاجة)
                val prefs = requireContext().getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
                prefs.edit()
                    .putInt("userId", user.id)          // ✅ مهم للفيفوريت/الويشليست
                    .putString("username", user.name)   // ✅ الاسم اللي هيظهر في Settings
                    .apply()

                findNavController().navigate(R.id.homeFragment)
            }
        }

        view.findViewById<View>(R.id.tvGoSignup)?.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)
        }
    }
}
