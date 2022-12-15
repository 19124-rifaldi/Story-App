package com.example.storyapp.view.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.Dummy
import com.example.storyapp.api.RegistResponse
import com.example.storyapp.api.RegisterRequest
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.AuthRepo
import com.example.storyapp.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
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
class RegisterViewModelTest{
    private lateinit var registerViewModel: RegisterViewModel
    @Mock
    private lateinit var authRepo: AuthRepo
    private val successDummy = Dummy.successRegisterDummy()
    private val failedDummy = Dummy.failedRegisterDummy()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        registerViewModel = RegisterViewModel(authRepo)
    }
    @Test
    fun `when registry is success and getting response`()= runTest{
        val expectedData = MutableLiveData<RegistResponse>()
        expectedData.value = successDummy
        val regData = RegisterRequest(
            "Yudi",
            "yudi@gmail.net",
            "123",
        )
        Mockito.`when`(authRepo.register(regData)).thenReturn(expectedData.value)
        registerViewModel.register(regData)
        advanceUntilIdle()
        val actualData = registerViewModel.regResp.getOrAwaitValue()
        Mockito.verify(authRepo).register(regData)
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

    @Test
    fun `when registry is failed and getting error response`()= runTest{
        val expectedData = MutableLiveData<RegistResponse>()
        expectedData.value = failedDummy
        val regData = RegisterRequest(
            "Yudi",
            "yudi@gmail.net",
            "123",
        )
        Mockito.`when`(authRepo.register(regData)).thenReturn(expectedData.value)
        registerViewModel.register(regData)
        advanceUntilIdle()
        val actualData = registerViewModel.regResp.getOrAwaitValue()
        Mockito.verify(authRepo).register(regData)
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

}