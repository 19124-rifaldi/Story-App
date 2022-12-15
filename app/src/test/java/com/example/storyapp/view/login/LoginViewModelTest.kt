package com.example.storyapp.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.storyapp.api.LoginRequest
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.AuthRepo
import com.example.storyapp.repo.TokenRepository
import com.example.storyapp.Dummy
import com.example.storyapp.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest{
    private lateinit var loginViewModel: LoginViewModel
    @Mock
    private lateinit var authRepo: AuthRepo
    @Mock
    private lateinit var tokenRepository: TokenRepository
    private val successDummy = Dummy.loginResponseDummy()
    private val falseDummy = Dummy.falsePasswordLogin()
    private val token = "kucingbelang"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        loginViewModel = LoginViewModel(authRepo,tokenRepository)
    }

    @Test
    fun `when login successful and response not null`()= runTest{
        val expectedData = MutableLiveData<LoginResponse>()
        expectedData.value = successDummy
        val loginData = LoginRequest(
            "yudi@gmail.net",
            "yudi123"
        )
        Mockito.`when`(authRepo.login(loginData)).thenReturn(expectedData.value)
        loginViewModel.loginToHome(loginData)
        val actualData = loginViewModel.login.getOrAwaitValue()
        Mockito.verify(authRepo).login(loginData)
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

    @Test
    fun `when login is failed and return error`()= runTest{
        val expectedData = MutableLiveData<LoginResponse>()
        expectedData.value = falseDummy
        val loginData = LoginRequest(
            "yudi@gmail.net",
            "yudi123"
        )
        Mockito.`when`(authRepo.login(loginData)).thenReturn(expectedData.value)
        loginViewModel.loginToHome(loginData)
        val actualData = loginViewModel.login.getOrAwaitValue()
        Mockito.verify(authRepo).login(loginData)
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

    @Test
    fun `when token is not null`(){
        val expectedData = MutableLiveData<String>()
        expectedData.value = token
        Mockito.`when`(tokenRepository.getToken()).thenReturn(expectedData.asFlow())
        val actualData = loginViewModel.getToken().getOrAwaitValue()
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

    @Test
    fun `when save token is successful`()= runTest{
       loginViewModel.saveToken(token)
        Mockito.verify(tokenRepository).saveToken(token)
    }
}