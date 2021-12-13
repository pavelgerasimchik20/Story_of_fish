package com.geras.fishistory.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.geras.fishistory.FisHistoryApplication
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityMainBinding
import com.geras.fishistory.presentation.FishAdapter
import com.geras.fishistory.presentation.SimpleItemTouchHelperCallback
import com.geras.fishistory.presentation.vm.FishViewModel
import com.geras.fishistory.presentation.vm.FishViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FishAdapter(::onDeleteFish, ::onClickAction)

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
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.mainRecycler)

        fishViewModel.allFish.observe(this@MainActivity) { fish ->
            adapter.replaceFishes(fish)
        }

        binding.fab.setOnClickListener {
            launcher.launch(Unit)
        }

        binding.filter.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val switchNameValue = prefs.getBoolean("switch_name", false)
        val switchWeightValue = prefs.getBoolean("switch_weight", false)
        val switchLocationValue = prefs.getBoolean("switch_location", false)
        if (switchNameValue) {
            adapter.sort("name")
        }
        if (switchWeightValue) {
            adapter.sort("weight")
        }
        if (switchLocationValue) {
            adapter.sort("location")
        }
    }


    private fun onDeleteFish(fish: Fish) {
        fishViewModel.onItemDismiss(fish)
    }

    private fun onClickAction(fish: Fish) {
        val intent = Intent(this, FullScreenActivity::class.java)
        intent.putExtra("FISH_PATH", fish.photoPath)
        startActivity(intent)
    }
}