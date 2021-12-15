package com.geras.fishistory.presentation.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.geras.fishistory.data.Fish
import com.geras.fishistory.data.FishRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private lateinit var repository: FishRepository
    private lateinit var viewModel: MainViewModel
    private lateinit var fish: Fish
    private lateinit var observer: Observer<List<Fish>>

    @Before
    fun setup(){
        fish = mock()
        repository = mock()
        viewModel = MainViewModel(repository)
        observer = mock()
        viewModel.allFish.observeForever(observer)
    }

    @Test
    fun when_launch_addOrUpdate_should_show_onChanged(){
        viewModel.addOrUpdateFish(fish)
        verify(observer).onChanged(any())
    }
}