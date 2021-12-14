package com.geras.fishistory.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.geras.fishistory.R
import com.geras.fishistory.databinding.ActivityFullScreenBinding
import java.io.File

class FullScreenActivity : AppCompatActivity() {

    private var _binding: ActivityFullScreenBinding? = null
    private val binding: ActivityFullScreenBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFullScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val path = intent?.getStringExtra("FISH_PATH")
        val photoFile = path?.let { File(it) }
        binding.ivFullScreen.load(photoFile) {
            error(R.drawable.ic_baseline_arrow_back_24)
            placeholder(R.drawable.river)
        }
        binding.share.setOnClickListener {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}