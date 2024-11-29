package com.example.cartzyapp.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import android.util.Patterns

class User() : RealmObject {
    @PrimaryKey
    var id: String = ""
    var username: String = ""
    var email: String = ""
        set(value) {
            if (!isValidEmail(value)) {
                return
            }
            field = value
        }
    var password: String = ""
        set(value) {
            if (!isValidPassword(value)) {
                return
            }
            field = value
        }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
