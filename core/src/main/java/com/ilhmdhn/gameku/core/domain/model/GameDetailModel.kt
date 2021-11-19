package com.ilhmdhn.gameku.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameDetailModel(
    val id: Int,
    val backgroundImage: String,
    val name: String,
    val nameOriginal: String,
    val description: String,
    val rating: Float,
    val released: String,
    val website: String,
    val isFavorite: Boolean
): Parcelable