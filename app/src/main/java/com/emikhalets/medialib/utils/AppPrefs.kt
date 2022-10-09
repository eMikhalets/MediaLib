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

    var moviesGenresSaved
        get() = sp.getBoolean(MOVIES_GENRES_SAVED, false)
        set(value) = sp.edit().putBoolean(MOVIES_GENRES_SAVED, value).apply()

    var tvGenresSaved
        get() = sp.getBoolean(TV_GENRES_SAVED, false)
        set(value) = sp.edit().putBoolean(TV_GENRES_SAVED, value).apply()

    var bookGenresSaved
        get() = sp.getBoolean(BOOK_GENRES_SAVED, false)
        set(value) = sp.edit().putBoolean(BOOK_GENRES_SAVED, value).apply()

    companion object {
        private const val NAME = "Main Preferences"
        private const val APP_LANGUAGE = "APP_LANGUAGE"
        private const val MOVIES_GENRES_SAVED = "MOVIES_GENRES_SAVED"
        private const val TV_GENRES_SAVED = "TV_GENRES_SAVED"
        private const val BOOK_GENRES_SAVED = "BOOK_GENRES_SAVED"

        const val DEF_APP_LANGUAGE = "en"
    }
}