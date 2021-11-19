package com.ilhmdhn.gameku.core.domain.repository

import com.ilhmdhn.gameku.core.data.Resource
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.model.GameListModel
import kotlinx.coroutines.flow.Flow

interface IGameRepository {

    fun getAllGame(): Flow<Resource<List<GameListModel>>>

    fun getGameDetail(id: Int): Flow<Resource<GameDetailModel>>

    fun getFavGame():Flow<List<GameListModel>>

    fun updateFavoriteGame(gameData: GameDetailModel, state: Boolean)
}