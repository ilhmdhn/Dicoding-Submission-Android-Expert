package com.ilhmdhn.gameku.core.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilhmdhn.gameku.core.R
import com.ilhmdhn.gameku.core.databinding.ListGameBinding
import com.ilhmdhn.gameku.core.domain.model.GameListModel

class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {
    private var listData = ArrayList<GameListModel>()
    var onItemClick: ((GameListModel) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newListData: List<GameListModel>?){
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)

//      masih menggunakan notifyDataSetChanged() karena masih belum bisa menerapkan DiffUtil di module juga belum dijelaskan
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ListGameBinding.bind(itemView)
        fun bind(data: GameListModel){
            with(binding){
                Glide.with(itemView.context)
                    .load(data.backgroundImgae)
                    .placeholder(R.drawable.ic_image_loading)
                    .into(gameImage)
                gameName.text = data.name
                gameRating.rating = data.rating
            }
        }

        init{
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
         ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_game, parent, false))


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size
}