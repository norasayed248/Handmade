package com.example.handmade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rowLanguage = view.findViewById<LinearLayout>(R.id.rowLanguage)

        rowLanguage.setOnClickListener {
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
}
