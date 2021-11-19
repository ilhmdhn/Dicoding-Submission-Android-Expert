package com.ilhmdhn.gameku.di

import com.ilhmdhn.gameku.core.domain.usecase.GameInteractor
import com.ilhmdhn.gameku.core.domain.usecase.GameUseCase
import com.ilhmdhn.gameku.detail.DetailViewModel
import com.ilhmdhn.gameku.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module{
viewModel {HomeViewModel(get())}
viewModel { DetailViewModel(get()) }
}