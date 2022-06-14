package com.dicoding.storyapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.storyapp.data.remote.MainRepository
import com.dicoding.storyapp.data.remote.Result
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
class SignupViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository
    private lateinit var signupViewModel: SignupViewModel
    private val dummyName = DataDummy.generateDummyName()
    private val dummyEmail = DataDummy.generateDummyEmail()
    private val dummyPassword = DataDummy.generateDummyPassword()

    @Before
    fun setUp() {
       signupViewModel = SignupViewModel(mainRepository)
    }

    @Test
    fun `when signUp Should Not Null and Return Success`() {
        val expectedResponse = MutableLiveData<Result<String>>()
        expectedResponse.value = Result.Success("User Created")
        `when`(signupViewModel.signUp(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedResponse)
        val actualResponse = signupViewModel.signUp(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(mainRepository).signUp(dummyName, dummyEmail, dummyPassword)
        Assert.assertTrue(actualResponse is Result.Success)
        Assert.assertNotNull(actualResponse)
    }

    @Test
    fun `when signUp Error and Return Error`() {
        val expectedResponse = MutableLiveData<Result<String>>()
        expectedResponse.value = Result.Error("HTTP 400 Bad Request")
        `when`(signupViewModel.signUp(dummyName, dummyEmail, dummyPassword)).thenReturn(expectedResponse)
        val actualResponse = signupViewModel.signUp(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()

        Mockito.verify(mainRepository).signUp(dummyName, dummyEmail, dummyPassword)
        Assert.assertTrue(actualResponse is Result.Error)
        Assert.assertNotNull(actualResponse)
    }
}