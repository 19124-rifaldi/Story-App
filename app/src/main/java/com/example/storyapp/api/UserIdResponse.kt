package com.example.storyapp.api

data class UserIdResponse(
	val error: Boolean,
	val message: String,
	val story: Story
)

data class Story(
	val photoUrl: String,
	val createdAt: String,
	val name: String,
	val description: String,
	val lon: Double,
	val id: String,
	val lat: Double
)

