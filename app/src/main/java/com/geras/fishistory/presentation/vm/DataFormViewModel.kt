package com.geras.fishistory.presentation.vm

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.geras.fishistory.data.FishRepository
import javax.inject.Inject

class DataFormViewModel @Inject constructor(private val repository: FishRepository): ViewModel() {

    private var bitmap: Bitmap? = null

   /* fun getPath(fish: Fish) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPath(fish)
        }
    }*/

}