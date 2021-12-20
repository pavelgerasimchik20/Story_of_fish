package com.geras.fishistory.presentation.vm

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geras.fishistory.data.Fish
import com.geras.fishistory.data.FishRoomDatabase
import com.geras.fishistory.data.MainRepositoryImpl
import com.geras.fishistory.presentation.settings.ThemeManager
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewModelTest : TestCase() {

    private lateinit var viewModel: MainViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, FishRoomDatabase::class.java)
            .allowMainThreadQueries().build()
        val repo = MainRepositoryImpl(db.fishDao())
        val manager = ThemeManager(context)
        viewModel = MainViewModel(repo,manager)
    }

    @Test
    fun testMainViewModel() {
        viewModel.addOrUpdateFish(fish = Fish("id", "name", "location", 1.0, "photoPath"))
        viewModel.onItemDismiss(fish = Fish("id", "name", "location", 1.0, "photoPath"))
        val result = viewModel.allFish.getOrAwaitValue().find {
            it.id == "id" &&
                    it.name == "name" &&
                    it.location == "location" &&
                    it.weight == 1.0 &&
                    it.photoPath == "photoPath"
        }
        assertThat(result != null).isTrue()
    }
}