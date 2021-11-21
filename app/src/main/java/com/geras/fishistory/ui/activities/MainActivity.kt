package com.geras.fishistory.ui.activities

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.geras.fishistory.FisHistoryApplication
import com.geras.fishistory.data.dataclasses.Fish
import com.geras.fishistory.databinding.ActivityMainBinding
import com.geras.fishistory.ui.SimpleItemTouchHelperCallback
import com.geras.fishistory.ui.adapter.FishAdapter
import com.geras.fishistory.ui.vm.FishViewModel
import com.geras.fishistory.ui.vm.FishViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FishAdapter(::onDeleteFish) {}
    private val launcher = registerForActivityResult(DataFormActivity.getCreateContract()) {
        if (it != null) {
            fishViewModel.addFish(it)
        }
    }

    private val fishViewModel: FishViewModel by viewModels {
        FishViewModelFactory((application as FisHistoryApplication).repository)
    }

    /*private val dismissHelperCallback = object : SimpleItemTouchHelperCallback.ItemTouchHelperDismissCallback {
        override fun onItemDismiss(position: Int) {
            fishViewModel.onItemDismiss(position)
            adapter.onItemDismiss(position)
        }
    }*/



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
    }

    override fun onResume() {
        super.onResume()
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
}