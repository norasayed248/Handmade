package com.example.handmade

import android.content.Context
import java.util.Locale

object LocaleHelper {

    private const val PREFS = "app_settings"
    private const val KEY_LANG = "language"

    fun setLanguage(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(
            config,
            context.resources.displayMetrics
        )

        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_LANG, lang)
            .apply()
    }

    fun getLanguage(context: Context): String {
        return context
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(KEY_LANG, "en") ?: "en"
    }
}
