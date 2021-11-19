package com.ilhmdhn.gameku.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ilhmdhn.gameku.core.data.Resource
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.usecase.GameUseCase
import kotlinx.coroutines.flow.Flow

class DetailViewModel(private val gameUseCase: GameUseCase): ViewModel() {
    private var gameDetail: Flow<Resource<GameDetailModel>>? = null

    fun setGameDetail(id: Int){
        gameDetail = gameUseCase.getDetailGame(id)
    }

    fun getGameDetail() = gameDetail?.asLiveData()

    fun updateFavoriteGame(game: GameDetailModel, newState: Boolean)= gameUseCase.updateFavorite(game, newState)
}