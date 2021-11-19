package com.ilhmdhn.gameku.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.ilhmdhn.gameku.R
import com.ilhmdhn.gameku.core.data.Resource
import com.ilhmdhn.gameku.core.domain.model.GameDetailModel
import com.ilhmdhn.gameku.core.domain.model.GameListModel
import com.ilhmdhn.gameku.databinding.ActivityDetailBinding
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private val detailViewModel: DetailViewModel by viewModel()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val dataGame = intent.getParcelableExtra<GameListModel>(EXTRA_DATA)
        if (dataGame != null) {
            detailViewModel.setGameDetail(dataGame.id)
            detailViewModel.getGameDetail()?.observe(this, { gameDetail ->
                if (gameDetail != null) {
                    when (gameDetail) {
                        is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE

                        is Resource.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            showDetail(gameDetail.data)
                        }

                        is Resource.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(this,gameDetail.message,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
    }

    private fun showDetail(data: GameDetailModel?){
        data?.let{
            binding?.ivDetailImage?.let {
                Glide.with(this)
                    .load(data.backgroundImage)
                    .placeholder(R.drawable.ic_image_loading)
                    .into(it)
            }
            with(binding?.content){
            this?.tvGameName?.text = data.name
            this?.tvGameDescription?.text = data.description
            this?.tvGameOriName?.text = data.nameOriginal
            this?.tvGameRating?.text = data.rating.toString()
            this?.tvGameRelease?.text = data.released
            this?.tvGameWebsite?.text = data.website
            }

            var favState = data.isFavorite
            setStatusFavorite(favState)
            binding?.fabFavorite?.setOnClickListener {
                favState = !favState
                detailViewModel.updateFavoriteGame(data, favState)
                setStatusFavorite(favState)
            }
        }
    }

    private fun setStatusFavorite(favState: Boolean){
        if (favState){
            binding?.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorited))
        } else{
            binding?.fabFavorite?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}