package com.example.storyapp.view.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.storyapp.Dummy
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.MainRepository
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest{
    private lateinit var mapsViewModel: MapsViewModel
    @Mock
    private lateinit var listStoryRepo: MainRepository
    @Mock
    private lateinit var tokenRepository: TokenRepository
    private val dummy = Dummy.storyListDummy()
    private val token = "kucingbelang"


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        mapsViewModel = MapsViewModel(tokenRepository,listStoryRepo)
    }

    @Test
    fun `when story by location is success and not null`()= runTest {
        val expectedData = dummy
        Mockito.`when`(listStoryRepo.getStoriesAndLocation(token,1)).thenReturn(expectedData)

        mapsViewModel.getMapsStory(token)
        Mockito.verify(listStoryRepo).getStoriesAndLocation(token,1)
        val actualData = mapsViewModel.story.getOrAwaitValue()
        assertNotNull(actualData)
        assertEquals(expectedData.listStory,actualData)

    }

    @Test
    fun `when token is not null`(){
        val expectedData = MutableLiveData<String>()
        expectedData.value = token
        Mockito.`when`(tokenRepository.getToken()).thenReturn(expectedData.asFlow())
        val actualData = mapsViewModel.getToken().getOrAwaitValue()
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }
}