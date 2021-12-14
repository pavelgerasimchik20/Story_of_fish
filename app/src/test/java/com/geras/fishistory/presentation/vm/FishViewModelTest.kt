package com.geras.fishistory.presentation.vm

import com.geras.fishistory.data.Fish
import com.geras.fishistory.data.FishRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.Assert
import org.junit.Test

class FishViewModelTest {

    val fish = Fish("id","name","location",1.0,"photoPath")

    @Test
    suspend fun addOrUpdateFish() {
        val repo = mock<FishRepository>()
        val viewModel = FishViewModel(repo)
        viewModel.addOrUpdateFish(fish)
        /*verify(repo).insert(fish)*/
        verify(repo, times(1)).insert(any())
    }

    @Test
    suspend fun getDataWhenRepositoryInsert() {
        val repo = mock<FishRepository>()
        val viewModel = FishViewModel(repo)

        whenever(repo.insert(any())).thenReturn(null)

        val result = viewModel.addOrUpdateFish(fish)
        Assert.assertEquals(null, result)
    }
}