package com.ilhmdhn.gameku.core.data.source.remote

import android.util.Log
import com.ilhmdhn.gameku.core.data.source.remote.network.ApiResponse
import com.ilhmdhn.gameku.core.data.source.remote.network.ApiService
import com.ilhmdhn.gameku.core.data.source.remote.response.GameDetailResponse
import com.ilhmdhn.gameku.core.data.source.remote.response.GameListResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getAllGame(): Flow<ApiResponse<List<GameListResult>>>{
        return flow{
            try{
                val response = apiService.getGameList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGame(id: String): Flow<ApiResponse<GameDetailResponse>>{
        return flow {
            try {
                val gameDetail = apiService.getGameDetail(id)
                emit(ApiResponse.Success(gameDetail))
            } catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}