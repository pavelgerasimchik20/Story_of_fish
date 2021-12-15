package com.geras.fishistory.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.geras.fishistory.FishHistoryApplication
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityMainBinding
import com.geras.fishistory.presentation.FishAdapter
import com.geras.fishistory.presentation.SimpleItemTouchHelperCallback
import com.geras.fishistory.presentation.vm.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter =
        FishAdapter(::onDeleteFish, ::onPictureClickAction, ::onDescriptionClickAction)

    private val launcher = registerForActivityResult(DataFormActivity.getCreateContract()) { fish ->
        if (fish != null) {
            mainViewModel.addOrUpdateFish(fish)
        }
    }

    private val mainViewModel: MainViewModel by viewModels {
        (application as FishHistoryApplication).appComponent.getViewModelFactory()
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

        mainViewModel.allFish.observe(this@MainActivity) { fish ->
            adapter.replaceFishes(fish)
        }

        binding.fab.setOnClickListener {
            launcher.launch(null)
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
        mainViewModel.onItemDismiss(fish)
    }

    private fun onPictureClickAction(fish: Fish) {
        val intent = Intent(this, FullScreenActivity::class.java)
        intent.putExtra("FISH_PATH", fish.photoPath)
        startActivity(intent)
    }

    private fun onDescriptionClickAction(fish: Fish) {
        launcher.launch(fish)
    }
}