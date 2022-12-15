package com.example.storyapp.view.createStory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.storyapp.Dummy
import com.example.storyapp.MainDispatcherRule
import com.example.storyapp.api.FileUploadResponse
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.repo.TokenRepository
import com.example.storyapp.repo.UploadStoryRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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
class CreateStoryViewModelTest{
    private lateinit var createStoryViewModel: CreateStoryViewModel
    @Mock
    private lateinit var storyRepo: UploadStoryRepo
    @Mock
    private lateinit var tokenRepository: TokenRepository
    private val successDummy = Dummy.successUploadStoryDummy()
    private val falseDummy = Dummy.failedUploadStoryDummy()
    private val token = "kucingbelang"
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup(){
        createStoryViewModel = CreateStoryViewModel(storyRepo,tokenRepository)
    }

    @Test
    fun `when upload story is successful and response is not null`()= runTest{
        val expectedData = MutableLiveData<FileUploadResponse>()
        expectedData.value = successDummy
        val image = MultipartBody.Part.create(
            "image".toRequestBody()
        )
        val desc = "ini deskripsi".toRequestBody()
        Mockito.`when`(storyRepo.uploadStory(image,desc,token)).thenReturn(expectedData.value)
        createStoryViewModel.uploadStory(image,desc,token)
        val actualData = createStoryViewModel.uploadResp.getOrAwaitValue()
        Mockito.verify(storyRepo).uploadStory(image,desc,token)
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

    @Test
    fun `when upload story is failed and response is error`()= runTest {
        val expectedData = MutableLiveData<FileUploadResponse>()
        expectedData.value = falseDummy
        val image = MultipartBody.Part.create(
            "image".toRequestBody()
        )
        val desc = "ini deskripsi".toRequestBody()
        Mockito.`when`(storyRepo.uploadStory(image, desc, token)).thenReturn(expectedData.value)
        createStoryViewModel.uploadStory(image, desc, token)
        val actualData = createStoryViewModel.uploadResp.getOrAwaitValue()
        Mockito.verify(storyRepo).uploadStory(image, desc, token)
        assertNotNull(actualData)
        assertEquals(expectedData.value, actualData)
    }
    @Test
    fun `when token is not null`(){
        val expectedData = MutableLiveData<String>()
        expectedData.value = token
        Mockito.`when`(tokenRepository.getToken()).thenReturn(expectedData.asFlow())
        val actualData = createStoryViewModel.getToken().getOrAwaitValue()
        assertNotNull(actualData)
        assertEquals(expectedData.value,actualData)
    }

}