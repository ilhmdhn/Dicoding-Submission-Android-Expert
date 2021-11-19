package com.ilhmdhn.gameku.core.utils
import com.ilhmdhn.gameku.core.data.source.local.entity.GameEntity
import com.ilhmdhn.gameku.core.data.source.remote.response.GameDetailResponse
import com.ilhmdhn.gameku.core.data.source.remote.response.GameListResult
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.model.GameListModel

object DataMapper {
    fun mapResponseToEntities(input: List<GameListResult>): List<GameEntity>{
        val gameList = ArrayList<GameEntity>()
        input.map{
            val game = GameEntity(
                id = it.id,
                name = it.name,
                backgroundImage = it.backgroundImage,
                rating = it.rating,
                nameOriginal = "",
                description = "",
                released = "",
                website = "",
                isFavorite = false
            )
            gameList.add(game)
        }
        return gameList
    }

    fun mapListGameEntitiesToDomain(input: List<GameEntity>): List<GameListModel> =
        input.map{
            GameListModel(
                id = it.id,
                name = it.name,
                backgroundImgae = it.backgroundImage,
                rating = it.rating,
            )
        }

    fun mapDetailGameResponseToEntities(input: GameDetailResponse):GameEntity{
        return GameEntity(
            id = input.id,
            backgroundImage = input.backgroundImage,
            name = input.name,
            nameOriginal = input.nameOriginal,
            description = input.description,
            rating = input.rating,
            released = input.released,
            website = input.website,
            isFavorite = false
        )
    }

    fun mapDetailGameEntitiesToDomain(input: GameEntity): GameDetailModel=
        GameDetailModel(
            id = input.id,
            backgroundImage = input.backgroundImage,
            name = input.name,
            nameOriginal = input.nameOriginal,
            description = input.description,
            rating = input.rating,
            released = input.released,
            website = input.website,
            isFavorite = input.isFavorite
        )

    fun mapDetailGameDomainToEntity(input: GameDetailModel) =
        with(input){
            GameEntity(
                id, name, backgroundImage, rating, nameOriginal, description, released, website, isFavorite
            )
        }
}