package com.geras.fishistory.ui.vm

import androidx.lifecycle.*
import com.geras.fishistory.data.database.FishRepository
import com.geras.fishistory.data.dataclasses.Fish
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FishViewModel(private val repository: FishRepository) : ViewModel() {

    val allFish: LiveData<List<Fish>> = repository.allFish.asLiveData()

    fun addFish(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(fish)
        }
    }

    fun onItemDismiss(position: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(position)
        }
    }
}

class FishViewModelFactory(private val repository: FishRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FishViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
