package com.ilhmdhn.gameku.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GameDetailResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("background_image")
	val backgroundImage: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("name_original")
	val nameOriginal: String,

	@field:SerializedName("description_raw")
	val description: String,

	@field:SerializedName("rating")
	val rating: Float,

	@field:SerializedName("released")
	val released: String,

	@field:SerializedName("website")
	val website: String
)