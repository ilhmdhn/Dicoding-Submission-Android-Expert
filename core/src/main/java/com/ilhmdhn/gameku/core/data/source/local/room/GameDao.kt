package com.ilhmdhn.gameku.core.data.source.local.room

import androidx.room.*
import com.ilhmdhn.gameku.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM gameentities")
    fun getAllGame(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGameList(game: List<GameEntity>)

    @Query("DELETE FROM gameentities")
    suspend fun deleteGameList()

    @Query("SELECT * FROM gameentities WHERE id = :id")
    fun getDetailGame(id: Int): Flow<GameEntity>

    @Query("SELECT * FROM gameentities WHERE isFavorite = 1")
    fun getFavGame(): Flow<List<GameEntity>>
    
    @Update
    suspend fun updateGame(game: GameEntity)

    @Update
    fun updateFavorite(data: GameEntity)
}