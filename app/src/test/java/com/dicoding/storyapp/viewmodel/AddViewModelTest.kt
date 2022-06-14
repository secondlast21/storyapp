package com.dicoding.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.storyapp.data.remote.MainRepository
import com.dicoding.storyapp.data.remote.Result
import com.dicoding.storyapp.data.remote.StoryRepository
import com.dicoding.storyapp.data.remote.response.LoginResult
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
class AddViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addViewModel: AddViewModel
    private val dummyToken = DataDummy.generateDummyToken()
    private val dummyPhoto = DataDummy.generateDummyPhoto()
    private val dummyDesc = DataDummy.generateDummyDesc()

    @Before
    fun setUp() {
        addViewModel = AddViewModel(storyRepository)
    }

    @Test
    fun `when Add Story Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<String>>()
        expectedResponse.value = Result.Success("Upload Success")
        `when`(addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedResponse)
        val actualResponse = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertNotNull(actualResponse)
    }

    @Test
    fun `when Add Story Error and Return Error`() {
        val expectedResponse = MutableLiveData<Result<String>>()
        expectedResponse.value = Result.Error("HTTP 401 Unauthorized")
        `when`(addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc)).thenReturn(expectedResponse)
        val actualResponse = addViewModel.addNewStory(dummyToken, dummyPhoto, dummyDesc).getOrAwaitValue()

        Mockito.verify(storyRepository).addNewStory(dummyToken, dummyPhoto, dummyDesc)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertNotNull(actualResponse)
    }
}