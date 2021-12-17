package com.geras.fishistory.presentation.vm

import androidx.lifecycle.*
import com.geras.fishistory.data.Fish
import com.geras.fishistory.data.FishRepository
import com.geras.fishistory.presentation.settings.ThemeManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: FishRepository,
    private val themeManager: ThemeManager
) : ViewModel() {

    val allFish: LiveData<List<Fish>> = repository.allFish.asLiveData()

    fun addOrUpdateFish(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(fish)
        }
    }

    fun changeTheme(regime: Boolean) {
        themeManager.changeTheme(regime)
    }

    fun onItemDismiss(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(fish)
        }
    }
}
