package com.example.challengechapter4

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.challengechapter4.databinding.ActivityMainBinding
import com.example.challengechapter4.viewmodel.MainViewModel
import androidx.activity.viewModels;
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.challengechapter4.domain.GameState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: MainViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if (it.state == GameState.START) {
                        binding.playerOption.batu.setBackgroundColor(Color.TRANSPARENT)
                        binding.playerOption.kertas.setBackgroundColor(Color.TRANSPARENT)
                        binding.playerOption.gunting.setBackgroundColor(Color.TRANSPARENT)

                        binding.computerOption.batu.setBackgroundColor(Color.TRANSPARENT)
                        binding.computerOption.kertas.setBackgroundColor(Color.TRANSPARENT)
                        binding.computerOption.gunting.setBackgroundColor(Color.TRANSPARENT)

                        binding.result.text = "VS"
                        binding.result.setTextColor(Color.RED)
                        binding.result.textSize = 48F
                        binding.result.setBackgroundColor(Color.TRANSPARENT)
                    }

                    if (it.state == GameState.FINISH) {
                        when (it.result) {
                            "player" -> {
                                binding.result.text = "Pemain 1 \n MENANG"
                                binding.result.setTextColor(Color.WHITE)
                                binding.result.textSize = 18F
                                binding.result.setBackgroundColor(Color.GREEN)
                            }
                            "computer" -> {
                                binding.result.text = "Pemain 2 \n MENANG"
                                binding.result.setTextColor(Color.WHITE)
                                binding.result.textSize = 18F
                                binding.result.setBackgroundColor(Color.GREEN)
                            }
                            "draw" -> {
                                binding.result.text = "DRAW"
                                binding.result.setTextColor(Color.WHITE)
                                binding.result.textSize = 22F
                                binding.result.setBackgroundColor(Color.BLUE)
                            }
                        }

                        when (it.chosen["computer"]){
                            "batu" -> binding.computerOption.batu.setBackgroundResource(R.drawable.rounded_corner)
                            "kertas" -> binding.computerOption.kertas.setBackgroundResource(R.drawable.rounded_corner)
                            "gunting" -> binding.computerOption.gunting.setBackgroundResource(R.drawable.rounded_corner)
                        }
                    }
                }
            }
        }

        binding.playerOption.batu.setOnClickListener {
            Log.d(TAG, "Player 1 memilih Batu")
            if (viewModel.uiState.value.state == GameState.FINISH) {
                showSnackBar(it); return@setOnClickListener
            }
            viewModel.startGame("batu")
            it.setBackgroundResource(R.drawable.rounded_corner)

        }

        binding.playerOption.gunting.setOnClickListener {
            Log.d(TAG, "Player 1 memilih Gunting")
            if (viewModel.uiState.value.state == GameState.FINISH) {
                showSnackBar(it); return@setOnClickListener
            }
            viewModel.startGame("gunting")
            it.setBackgroundResource(R.drawable.rounded_corner)

        }

        binding.playerOption.kertas.setOnClickListener {
            Log.d(TAG, "Player 1 memilih Kertas")
            if (viewModel.uiState.value.state == GameState.FINISH) {
                showSnackBar(it); return@setOnClickListener
            }
            viewModel.startGame("kertas")
            it.setBackgroundResource(R.drawable.rounded_corner)
        }

        binding.refresh.setOnClickListener {
            viewModel.restartGame();
        }
    }

    private fun showSnackBar(it: View) {
        Snackbar.make(
            it,
            "Anda sudah memilih restart terlebih dahulu",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        val TAG = MainActivity::class.simpleName
    }
}