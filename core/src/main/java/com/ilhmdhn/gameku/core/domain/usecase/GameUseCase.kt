package com.ilhmdhn.gameku.core.domain.usecase

import com.ilhmdhn.gameku.core.data.Resource
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.model.GameListModel
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGame(): Flow<Resource<List<GameListModel>>>

    fun getFavGame(): Flow<List<GameListModel>>

    fun getDetailGame(id: Int): Flow<Resource<GameDetailModel>>

    fun updateFavorite(game: GameDetailModel, state: Boolean)
}