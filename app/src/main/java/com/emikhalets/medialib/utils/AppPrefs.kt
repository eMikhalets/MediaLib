package com.emikhalets.medialib.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPrefs @Inject constructor(@ApplicationContext context: Context) {

    private var sp: SharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    var appLanguage
        get() = sp.getString(APP_LANGUAGE, DEF_APP_LANGUAGE) ?: DEF_APP_LANGUAGE
        set(value) = sp.edit().putString(APP_LANGUAGE, value).apply()

    companion object {
        private const val NAME = "Main Preferences"
        private const val APP_LANGUAGE = "APP_LANGUAGE"

        const val DEF_APP_LANGUAGE = "en"
    }
}