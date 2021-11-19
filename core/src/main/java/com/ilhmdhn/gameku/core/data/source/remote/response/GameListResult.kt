package com.ilhmdhn.gameku.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResult(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("background_image")
	val backgroundImage: String,

	@field:SerializedName("rating")
	val rating: Float
)