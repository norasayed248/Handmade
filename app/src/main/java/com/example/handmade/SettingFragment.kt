package com.example.handmade

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class SettingFragment : Fragment(R.layout.settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ده صف اللغة في settings.xml
        val rowLanguage = view.findViewById<View>(R.id.rowLanguage)

        rowLanguage.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val items = arrayOf(
            getString(R.string.english),
            getString(R.string.arabic)
        )

        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.language))
            .setItems(items) { _, which ->
                val lang = if (which == 0) "en" else "ar"
                LocaleHelper.setLanguage(requireContext(), lang)
                requireActivity().recreate()
            }
            .show()
    }
}
