package com.ilhmdhn.gameku.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ilhmdhn.gameku.core.domain.usecase.GameUseCase

class FavoriteViewModel(gameUseCase: GameUseCase): ViewModel() {
    val game = gameUseCase.getFavGame().asLiveData()
}