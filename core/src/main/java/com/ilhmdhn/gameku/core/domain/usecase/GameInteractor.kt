package com.ilhmdhn.gameku.core.domain.usecase

import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.repository.IGameRepository

class GameInteractor(private val gameRepository: IGameRepository): GameUseCase {
    override fun getAllGame() = gameRepository.getAllGame()

    override fun getFavGame() = gameRepository.getFavGame()

    override fun getDetailGame(id: Int) = gameRepository.getGameDetail(id)

    override fun updateFavorite(game: GameDetailModel, state: Boolean) =  gameRepository.updateFavoriteGame(game, state)
}