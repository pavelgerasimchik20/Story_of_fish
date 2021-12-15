package com.geras.fishistory.presentation.vm

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.geras.fishistory.data.Fish
import com.geras.fishistory.data.FishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
class DataFormViewModel @Inject constructor(private val repository: FishRepository): ViewModel() {

    private var path: String? = null
    private var bitmap: Bitmap? = null

    fun addOrUpdateFish(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(fish)
        }
    }

    fun onItemDismiss(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(fish)
        }
    }
}*/
