package com.dicoding.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.storyapp.data.remote.MainRepository
import com.dicoding.storyapp.data.remote.Result
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
class SigninViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository
    private lateinit var signinViewModel: SigninViewModel
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()
    private val dummyLogin = DataDummy.generateDummySignin()

    @Before
    fun setUp() {
        signinViewModel = SigninViewModel(mainRepository)
    }

    @Test
    fun `when signIn Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Success(dummyLogin)
        `when`(signinViewModel.signIn(dummyEmail, dummyPassword)).thenReturn(expectedResponse)
        val actualResponse = signinViewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(mainRepository).signIn(dummyEmail, dummyPassword)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertNotNull(actualResponse)
    }

    @Test
    fun `when signIn Error and Return Error`() {
        val expectedResponse = MutableLiveData<Result<LoginResult>>()
        expectedResponse.value = Result.Error("HTTP 401 Unauthorized")
        `when`(signinViewModel.signIn(dummyEmail, dummyPassword)).thenReturn(expectedResponse)
        val actualResponse = signinViewModel.signIn(dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(mainRepository).signIn(dummyEmail, dummyPassword)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertNotNull(actualResponse)
    }
}