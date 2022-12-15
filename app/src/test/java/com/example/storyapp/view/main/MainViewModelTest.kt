package com.example.storyapp.view.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.example.storyapp.Dummy
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.api.ListStoryItem
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.MainRepository
import com.example.storyapp.repo.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest{
    private lateinit var mainViewModel: MainViewModel
    @Mock
    private lateinit var listStoryRepo: MainRepository
    @Mock
    private lateinit var tokenRepository: TokenRepository
    private val dummy = Dummy.pagingListDummy()
    private val token = "kucingbelang"

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        mainViewModel = MainViewModel(tokenRepository,listStoryRepo)
    }

    @Test
    fun `when Get Story List Should Not Null and response equals`() = runTest {
        val dummyStory = dummy
        val data: PagingData<ListStoryItem> = StoryPagingSource.snapshot(dummyStory)
        val expectedData = MutableLiveData<PagingData<ListStoryItem>>()
        expectedData.value = data

        Mockito.`when`(listStoryRepo.getAllStories(token)).thenReturn(expectedData)
        val actualQuote: PagingData<ListStoryItem> = mainViewModel.getAllStories(token).getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = UserStoryAdapter.DIFF_CALLBACK,
            updateCallback = NoopListUpdateCallback.noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)

    }


    @Test
    fun `when token is not null`(){
        val expectedData = MutableLiveData<String>()
        expectedData.value = token
        Mockito.`when`(tokenRepository.getToken()).thenReturn(expectedData.asFlow())
        val actualData = mainViewModel.getToken().getOrAwaitValue()
        Assert.assertNotNull(actualData)
        Assert.assertEquals(expectedData.value, actualData)
    }

    @Test
    fun `when token is removed`()= runTest(){
        mainViewModel.removeToken()
        Mockito.verify(tokenRepository).removeToken()
    }



}

