package com.example.encryptedpreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

/**
 * EncryptedSharedPreferences implementation demo
 * @author Nav Singh
 */
class MainActivity : AppCompatActivity() {
    private lateinit var encryptedSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                this.applicationContext,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val name = encryptedSharedPreferences.getString("Name", "")

        Log.e("Value", "from sharedPreferences-: $name")

        // use the shared preferences same as  we use the normal
        //if value is not present save it
        name?.let {
            encryptedSharedPreferences.edit {
                putString("Name", "Security")
            }
        }

    }
}
