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

@ExperimentalCoroutinesApi
class MainRepositoryTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiService: ApiService
    private val dummyName = DataDummy.generateDummyName()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
        apiService = FakeApiService()
    }

    @Test
    fun `when signUp Should Not Null and Return Success`() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummySignupTrue()
        val actualResponse = apiService.register(dummyName, dummyEmail, dummyPassword)
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }

    @Test
    fun `when signIn Should Not Null and Return LoginResult `() = mainCoroutineRule.runBlockingTest {
        val expectedResponse = DataDummy.generateDummySignin()
        val actualResponse = apiService.login(dummyEmail, dummyPassword).loginResult
        Assert.assertNotNull(actualResponse)
        Assert.assertEquals(expectedResponse, actualResponse)
    }
}