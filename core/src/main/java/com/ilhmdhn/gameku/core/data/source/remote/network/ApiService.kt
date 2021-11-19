package com.ilhmdhn.gameku.core.data.source.remote.network

import com.ilhmdhn.gameku.core.BuildConfig
import com.ilhmdhn.gameku.core.data.source.remote.response.GameDetailResponse
import com.ilhmdhn.gameku.core.data.source.remote.response.GameListResponse
import retrofit2.http.*

interface ApiService {
    @GET("games")
    suspend fun getGameList(
        @Query("key") key: String = BuildConfig.API_KEY,
        @Query("page_size") size: String = "20"
    ): GameListResponse

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") idGame: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): GameDetailResponse
}