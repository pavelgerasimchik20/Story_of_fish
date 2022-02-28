package com.geras.fishistory.presentation.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.geras.fishistory.FishHistoryApplication
import com.geras.fishistory.R
import com.geras.fishistory.data.Fish
import com.geras.fishistory.databinding.ActivityMainBinding
import com.geras.fishistory.presentation.FishAdapter
import com.geras.fishistory.presentation.SimpleItemTouchHelperCallback
import com.geras.fishistory.presentation.vm.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
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
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchTheme.isChecked =
            AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES

        binding.poster?.setBackgroundResource(R.drawable.my_animation)
        binding.switchTheme.setOnCheckedChangeListener { _, _ ->
            mainViewModel.changeTheme(binding.switchTheme.isChecked)
        }

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
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onResume() {
        prefs.getBoolean("switch_name", false).let {
            if (it) {
                adapter.sort("name")
            }
        }
        prefs.getBoolean("switch_weight", false).let {
            if (it) {
                adapter.sort("weight")
            }
        }
        prefs.getBoolean("switch_location", false).let {
            if (it) {
                adapter.sort("location")
            }
        }
        super.onResume()
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
