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
import com.geras.fishistory.presentation.FishViewModel
import com.geras.fishistory.presentation.FishViewModelFactory
import com.geras.fishistory.presentation.SimpleItemTouchHelperCallback


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = FishAdapter(::onDeleteFish)/*::showFullScreanWithPhoto()*/

    /*private fun showFullScreanWithPhoto(uri: String) {
        val fishFragment: Fragment = FishFragment.newInstance(uri)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fishFragment)
        transaction.commitNowAllowingStateLoss()
    }*/

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