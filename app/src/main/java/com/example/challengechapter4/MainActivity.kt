package com.example.challengechapter4

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.challengechapter4.databinding.ActivityMainBinding
import com.example.challengechapter4.domain.GameState
import com.example.challengechapter4.viewmodel.MainViewModel
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

                        binding.tvResult.setText(R.string.initial_text_result)
                        binding.tvResult.setTextColor(Color.RED)
                        binding.tvResult.textSize = 48F
                        binding.tvResult.setBackgroundColor(Color.TRANSPARENT)
                    }

                    if (it.state == GameState.FINISH) {
                        when (it.result) {
                            "player" -> {
                                binding.tvResult.setText(R.string.player_win_result)
                                binding.tvResult.text = ""
                                binding.tvResult.setTextColor(Color.WHITE)
                                binding.tvResult.textSize = 18F
                                binding.tvResult.setBackgroundColor(Color.GREEN)
                            }
                            "computer" -> {
                                binding.tvResult.setText(R.string.com_win_result)
                                binding.tvResult.setTextColor(Color.WHITE)
                                binding.tvResult.textSize = 18F
                                binding.tvResult.setBackgroundColor(Color.GREEN)
                            }
                            "draw" -> {
                                binding.tvResult.setText(R.string.draw_result)
                                binding.tvResult.setTextColor(Color.WHITE)
                                binding.tvResult.textSize = 22F
                                binding.tvResult.setBackgroundColor(Color.BLUE)
                            }
                        }

                        when (it.chosen["computer"]) {
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

        binding.ibRefresh.setOnClickListener {
            viewModel.restartGame()
        }
    }

    /**
     * Function to show a Snackbar with message "Anda sudah memilih restart terlebih dahulu"
     * @param it the view where the Snackbar will be displayed
     */
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