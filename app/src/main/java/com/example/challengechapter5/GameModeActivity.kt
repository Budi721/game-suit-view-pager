package com.example.challengechapter5

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.challengechapter5.databinding.ActivityGameModeBinding
import com.example.challengechapter5.domain.GameMode
import com.example.challengechapter5.domain.UserPrefConstant
import com.google.android.material.snackbar.Snackbar

class GameModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameModeBinding

    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityGameModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences(
            UserPrefConstant.SHARED_PREF_KEY,
            Context.MODE_PRIVATE,
        )

        val username = pref.getString(UserPrefConstant.USERNAME_KEY, "Sayang")

        val snackBar = Snackbar.make(
            binding.root,
            "Selamat Datang $username",
            Snackbar.LENGTH_SHORT
        )
        snackBar.setAction("Tutup") { snackBar.dismiss() }
        snackBar.show()

        binding.tvPvpModeDesc.text = resources.getString(R.string.pvp_mode_desc, username)
        binding.tvComModeDesc.text = resources.getString(R.string.com_mode_desc, username)
        val editor = pref.edit()
        val intent = Intent(this, MainActivity::class.java)
        binding.imgViewPvpMode.setOnClickListener{
            editor.putString(UserPrefConstant.GAMEMODE_KEY, GameMode.VERSUS_PLAYER.name)
            editor.apply()
            startActivity(intent)
        }
        binding.imgViewComMode.setOnClickListener {
            editor.putString(UserPrefConstant.GAMEMODE_KEY, GameMode.VERSUS_COM.name)
            editor.apply()
            startActivity(intent)
        }
    }
}