package com.example.handmade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.signup_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // زرار Create Account
        view.findViewById<MaterialButton>(R.id.btnCreate)?.setOnClickListener {
            // بعد إنشاء الحساب (هتتربط ب Room بعدين) نرجع للـ Home أو Login
            findNavController().navigate(R.id.homeFragment)
        }

    }
}
