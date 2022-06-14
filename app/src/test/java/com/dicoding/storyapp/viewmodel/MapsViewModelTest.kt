package com.dicoding.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.storyapp.data.remote.MainRepository
import com.dicoding.storyapp.data.remote.Result
import com.dicoding.storyapp.data.remote.StoryRepository
import com.dicoding.storyapp.data.remote.response.ListStoryItem
import com.dicoding.storyapp.utils.DataDummy
import com.dicoding.storyapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStories = DataDummy.generateDummyListStories()
    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when Maps Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<List<ListStoryItem>>>()
        expectedResponse.value = Result.Success(dummyStories)
        `when`(mapsViewModel.getLocation(dummyToken)).thenReturn(expectedResponse)
        val actualResponse = mapsViewModel.getLocation(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getLocation(dummyToken)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(dummyStories.size, (actualResponse as Result.Success).data.size)
    }

    @Test
    fun `when Maps Error and Return Error`() {
        val expectedResponse = MutableLiveData<Result<List<ListStoryItem>>>()
        expectedResponse.value = Result.Error("Error")
        `when`(mapsViewModel.getLocation(dummyToken)).thenReturn(expectedResponse)
        val actualResponse = mapsViewModel.getLocation(dummyToken).getOrAwaitValue()

        Mockito.verify(storyRepository).getLocation(dummyToken)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertNotNull(actualResponse)
    }
}