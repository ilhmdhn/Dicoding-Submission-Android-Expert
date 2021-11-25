package com.ilhmdhn.gameku.core.ui

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.ilhmdhn.gameku.core.domain.model.GameListModel

class GameDiffCallback(private val oldListGame: List<GameListModel>, private val newListGame: List<GameListModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldListGame.size

    override fun getNewListSize(): Int = newListGame.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldListGame[oldItemPosition].name === newListGame.get(newItemPosition).backgroundImgae
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, name, backgroundImage) = oldListGame[oldItemPosition]
        val (_, name1, backgroundImage1) = newListGame[newItemPosition]

        return name == name1 && backgroundImage == backgroundImage1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}