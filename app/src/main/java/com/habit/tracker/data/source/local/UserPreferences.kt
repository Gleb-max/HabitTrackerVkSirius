package com.habit.tracker.data.source.local

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import com.google.gson.Gson
import com.habit.tracker.di.ApplicationScope
import com.habit.tracker.domain.entity.User
import javax.inject.Inject

@ApplicationScope
class UserPreferences @Inject constructor(context: Application) {

    private val prefs = EncryptedSharedPreferences.create(
        PREF_NAME,
        KEY_USER,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    private val gson = Gson()

    fun getUser(): User = try {
        gson.fromJson(prefs.getString(KEY_USER, ""), User::class.java)
    } catch (exc: Exception) {
        exc.printStackTrace()
        User.emptyUser()
    }

    fun saveUser(user: User) = prefs.edit().apply {
        putString(KEY_USER, gson.toJson(user))
    }.commit()

    companion object {

        private const val PREF_NAME = "user_prefs"
        private const val KEY_USER = "user"
    }
}