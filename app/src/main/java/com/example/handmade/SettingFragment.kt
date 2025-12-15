package com.example.handmade

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvUserName = view.findViewById<TextView>(R.id.tvUserName)
        val prefs = requireContext().getSharedPreferences("auth", Context.MODE_PRIVATE)
        tvUserName.text = prefs.getString("username", getString(R.string.guest))

        val rowLanguage = view.findViewById<View>(R.id.rowLanguage)
        rowLanguage.setOnClickListener {

            // خليها من الـ strings عشان تتترجم هي كمان
            val items = arrayOf(
                getString(R.string.arabic),
                getString(R.string.english)
            )

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.choose_language))
                .setItems(items) { _, which ->
                    val lang = if (which == 0) "ar" else "en"

                    // 1) طبّقي اللغة
                    LocaleHelper.setLanguage(requireContext(), lang)

                    // 2) خزّنيها (اختياري لأن LocaleHelper عندك بيخزن أصلاً)
                    requireContext()
                        .getSharedPreferences("settings", Context.MODE_PRIVATE)
                        .edit()
                        .putString("app_lang", lang)
                        .apply()

                    // 3) اعملي refresh للـ UI
                    requireActivity().recreate()
                }
                .show()
        }
    }
}
