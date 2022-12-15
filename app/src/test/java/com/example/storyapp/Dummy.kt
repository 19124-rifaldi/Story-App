package com.example.storyapp

import com.example.storyapp.api.*

object Dummy {
    fun detailDummyStory(): Story {
        return Story(
            "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
            "2022-01-08T06:34:18.598Z",
            "Yuda",
            "Lorem Ipsum",
            -16.002,
            "story-FvU4u0Vp2S3PMsFg",
            -10.003
        )
    }
    fun detailUserStory():UserIdResponse{
        return UserIdResponse(
            false,
            "success",
            detailDummyStory()
        )
    }
    fun loginResponseDummy(): LoginResponse{
        return LoginResponse(
            LoginResult(
                "yudi",
                "123qwerty",
                "123qwerty"
            ),
            false,
            "success"
        )
    }
    fun falsePasswordLogin(): LoginResponse{
        return LoginResponse(
            LoginResult(
                "yudi",
                "123qwerty",
                "123qwerty"
            ),
            true,
            "password is wrong"
        )
    }

    fun successRegisterDummy(): RegistResponse{
        return RegistResponse(
            false,
            "account is created"
        )
    }

    fun failedRegisterDummy(): RegistResponse{
        return RegistResponse(
            true,
            "error"
        )
    }
    fun successUploadStoryDummy(): FileUploadResponse{
        return FileUploadResponse(
            false,
            "upload is successful"
        )
    }

    fun failedUploadStoryDummy(): FileUploadResponse{
        return FileUploadResponse(
            true,
            "upload is failed"
        )
    }

    fun storyListDummy(): GetAllStoriesResponse {
        val list = arrayListOf<ListStoryItem>()
        for (i in 0..10){
            val listStory = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
            "Lorem Ipsum",
            "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
            "2022-01-08T06:34:18.598Z",
            -10.212,
            -16.002
            )
            list.add(listStory)
        }
        return GetAllStoriesResponse(
            list,
            false,
            "success"
        )
    }

    fun pagingListDummy (): List<ListStoryItem> {
        val list = arrayListOf<ListStoryItem>()
        for (i in 0..10){
            val listStory = ListStoryItem(
                "story-FvU4u0Vp2S3PMsFg",
                "Dimas",
                "Lorem Ipsum",
                "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png",
                "2022-01-08T06:34:18.598Z",
                -10.212,
                -16.002
            )
            list.add(listStory)
        }
        return list
    }

}