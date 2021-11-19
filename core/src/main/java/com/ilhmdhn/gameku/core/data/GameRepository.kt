package com.ilhmdhn.gameku.core.data

import com.ilhmdhn.gameku.core.data.source.local.LocalDataSource
import com.ilhmdhn.gameku.core.data.source.remote.RemoteDataSource
import com.ilhmdhn.gameku.core.data.source.remote.network.ApiResponse
import com.ilhmdhn.gameku.core.data.source.remote.response.GameDetailResponse
import com.ilhmdhn.gameku.core.data.source.remote.response.GameListResult
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.model.GameListModel
import com.ilhmdhn.gameku.core.domain.repository.IGameRepository
import com.ilhmdhn.gameku.core.utils.AppExecutors
import com.ilhmdhn.gameku.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IGameRepository {

    companion object{
        var reload: Boolean = false
    }

    override fun getAllGame(): Flow<Resource<List<GameListModel>>> =
        object : NetworkBoundResource<List<GameListModel>, List<GameListResult>>(){
            override fun loadFromDB(): Flow<List<GameListModel>> {
                return localDataSource.getAllGame().map { DataMapper.mapListGameEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<GameListModel>?): Boolean {
                return data.isNullOrEmpty() || reload
            }

            override suspend fun createCall(): Flow<ApiResponse<List<GameListResult>>> =
                remoteDataSource.getAllGame()

            override suspend fun saveCallResult(data: List<GameListResult>) {
                localDataSource.deleteGameList()
                val gameList = DataMapper.mapResponseToEntities(data)
                localDataSource.insertGameList(gameList)
            }
        }.asFlow()

    override fun getGameDetail(id: Int): Flow<Resource<GameDetailModel>> =
        object: NetworkBoundResource<GameDetailModel, GameDetailResponse>(){
            override fun loadFromDB(): Flow<GameDetailModel> {
                return localDataSource.getDetailGame(id).map{dataGame->
                    DataMapper.mapDetailGameEntitiesToDomain(dataGame)
                }
            }

            override fun shouldFetch(data: GameDetailModel?): Boolean =
                data?.nameOriginal.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<GameDetailResponse>> =
                remoteDataSource.getDetailGame(id.toString())


            override suspend fun saveCallResult(data: GameDetailResponse) {
                val game = DataMapper.mapDetailGameResponseToEntities(data)
                localDataSource.updateGame(game, false)
            }
        }.asFlow()

    override fun getFavGame(): Flow<List<GameListModel>> {
        return localDataSource.getfavGame().map{
            DataMapper.mapListGameEntitiesToDomain(it)
        }
    }


    override fun updateFavoriteGame(gameData: GameDetailModel, state: Boolean) {
        val gameEntity = DataMapper.mapDetailGameDomainToEntity(gameData)
        appExecutors.diskIO().execute { localDataSource.updateFavorite(gameEntity, state) }
    }
}