package com.ilhmdhn.gameku.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ilhmdhn.gameku.core.domain.usecase.GameUseCase

class HomeViewModel(gameUseCase: GameUseCase): ViewModel() {
    val gameList = gameUseCase.getAllGame().asLiveData()
}