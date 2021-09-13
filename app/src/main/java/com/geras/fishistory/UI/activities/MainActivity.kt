package com.geras.fishistory.UI.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.geras.fishistory.FisHistoryApplication
import com.geras.fishistory.FishAdapter
import com.geras.fishistory.databinding.ActivityMainBinding
import com.geras.fishistory.viewmodel.FishViewModel
import com.geras.fishistory.viewmodel.FishViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FishAdapter {
        Toast.makeText(
            this,
            "you clicked some item of the recycler",
            Toast.LENGTH_SHORT
        ).show()
    }
    private val launcher = registerForActivityResult(DataFormActivity.getCreateContract()) {
        if (it != null) {
            fishViewModel.addFish(it)
        }
    }

    private val fishViewModel: FishViewModel by viewModels {
        FishViewModelFactory((application as FisHistoryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecycler.layoutManager = LinearLayoutManager(this)

        binding.mainRecycler.adapter = adapter

        binding.fab.setOnClickListener {
            launcher.launch(Unit)
        }

        fishViewModel.allFish.observe(this@MainActivity) { fish ->
              adapter.replaceFishes(fish)
        }

        binding.filter.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val switchNameValue = prefs.getBoolean("switch_name", false)
        val switchWeightValue = prefs.getBoolean("switch_weight", false)
        val switchLocationValue = prefs.getBoolean("switch_location", false)
        if (switchNameValue) {
            adapter.sort("name")
        } else {
            shuffle()
        }
        if (switchWeightValue) {
            adapter.sort("weight")
        } else {
            shuffle()
        }
        if (switchLocationValue) {
            adapter.sort("location")
        } else {
            shuffle()
        }
    }

    private fun shuffle() {
        adapter.fishList.shuffle()
    }
}