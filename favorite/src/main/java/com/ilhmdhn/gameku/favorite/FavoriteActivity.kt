package com.ilhmdhn.gameku.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ilhmdhn.gameku.core.ui.HomeAdapter
import com.ilhmdhn.gameku.detail.DetailActivity
import com.ilhmdhn.gameku.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private val gameAdapter = HomeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.progressBar?.visibility = View.GONE

        loadKoinModules(favoriteModule)
        supportActionBar?.title = getString(R.string.favorite_game)
        getFavData()

        gameAdapter.onItemClick = {selectedData->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
    }

    private fun getFavData(){
        favoriteViewModel.game.observe(this, {game ->
            binding?.progressBar?.visibility = View.GONE
            gameAdapter.setData(game)
            if (game.isNullOrEmpty()){
                binding?.tvEmpty?.visibility = View.VISIBLE
            }else{
            with(binding?.rvFav){
                this?.layoutManager = GridLayoutManager(applicationContext, 2)
                this?.setHasFixedSize(true)
                this?.adapter = gameAdapter
            }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvFav?.adapter = null
        _binding = null
        unloadKoinModules(favoriteModule)
    }
}