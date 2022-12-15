package com.example.storyapp.view.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.DetailRepository
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.example.storyapp.Dummy
import com.example.storyapp.MainDispatcherRule

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{
    private lateinit var detailViewModel: DetailViewModel
    @Mock
    private lateinit var detailRepository: DetailRepository
    @Mock
    private lateinit var tokenRepository: TokenRepository
    private val dummy = Dummy.detailUserStory()
    private val token = "kucingbelang"
    private val id = "kucingbelang123"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        detailViewModel = DetailViewModel(detailRepository,tokenRepository)
    }

    @Test
    fun `when get detail is success and response not null`()= runTest {
        val expectedData = dummy
        `when`(detailRepository.getStoryByID(token,id)).thenReturn(expectedData)

        detailViewModel.getStoryByID(token,id)
        Mockito.verify(detailRepository).getStoryByID(token,id)
        val actualData = detailViewModel.idUser.getOrAwaitValue()
        assertNotNull(actualData.story)
        assertEquals(expectedData.story,actualData.story)

    }

    @Test
    fun `when token is not null`(){
        val expectedData = MutableLiveData<String>()
        expectedData.value = token
        `when`(tokenRepository.getToken()).thenReturn(expectedData.asFlow())
        val actualData = detailViewModel.getToken().getOrAwaitValue()
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }


}