package com.dicoding.storyapp.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.storyapp.data.remote.retrofit.ApiService
import com.dicoding.storyapp.data.retrofit.FakeApiService
import com.dicoding.storyapp.utils.DataDummy
import com.dicoding.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var apiService: ApiService
    private val dummyPhoto = DataDummy.generateDummyPhoto()
    private val dummyDesc = DataDummy.generateDummyDesc()
    private val dummyToken = DataDummy.generateDummyToken()

    @Before
    fun setUp() {
        apiService = FakeApiService()
    }

    @Test
    fun `when addNewstory Should Not Null and Return Success`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummyAddStories()
        val actualResponse = apiService.addNewStory(dummyToken, dummyPhoto, dummyDesc)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when getLocation Should Not Null and Return ListStoryItem `() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummyStories()
        val actualResponse = apiService.locationAllStories(dummyToken, 50, 1)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse.listStory.size, actualResponse.listStory.size)
    }
}