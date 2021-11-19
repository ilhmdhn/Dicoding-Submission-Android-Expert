package com.ilhmdhn.gameku.core.data.source.local

import com.ilhmdhn.gameku.core.data.source.local.entity.GameEntity
import com.ilhmdhn.gameku.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {

    fun getAllGame(): Flow<List<GameEntity>> = gameDao.getAllGame()

    fun getfavGame(): Flow<List<GameEntity>> = gameDao.getFavGame()

    suspend fun insertGameList(game: List<GameEntity>) = gameDao.insertGameList(game)

    suspend fun deleteGameList() = gameDao.deleteGameList()

    fun getDetailGame(id: Int): Flow<GameEntity> = gameDao.getDetailGame(id)

    suspend fun updateGame(game: GameEntity, favState: Boolean){
        game.isFavorite = favState
        gameDao.updateGame(game)
    }

    fun updateFavorite(game: GameEntity, favState: Boolean){
        game.isFavorite = favState
        gameDao.updateFavorite(game)
    }
}
