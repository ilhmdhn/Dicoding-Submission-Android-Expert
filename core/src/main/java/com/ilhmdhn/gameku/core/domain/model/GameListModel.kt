package com.ilhmdhn.gameku.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GameListModel(
    val id: Int,
    val name: String,
    val backgroundImgae: String,
    val rating: Float
): Parcelable