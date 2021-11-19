package com.ilhmdhn.gameku.home

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.ilhmdhn.gameku.R
import com.ilhmdhn.gameku.core.data.Resource
import com.ilhmdhn.gameku.core.ui.HomeAdapter
import com.ilhmdhn.gameku.databinding.ActivityMainBinding
import com.ilhmdhn.gameku.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()
    private val homeAdapter = HomeAdapter()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        getData()

        binding?.swipeLayout?.setOnRefreshListener {
            binding?.swipeLayout?.isRefreshing = false
        }

        homeAdapter.onItemClick = {selectedData->
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
        startActivity(intent)}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_favorite-> {
                val uri = Uri.parse("gameku://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getData() {
        homeViewModel.gameList.observe(this,{game ->
            when(game){
                is Resource.Loading -> binding?.progressBar?.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    homeAdapter.setData(game.data)
                }
                is Resource.Error ->{
                    binding?.progressBar?.visibility = View.GONE
                    binding?.tvError?.text = "${R.string.error} ${game.message}"
                }
            }
        })

        with(binding?.rvFav){
            this?.layoutManager = GridLayoutManager(this@MainActivity, 2)
            this?.setHasFixedSize(true)
            this?.adapter = homeAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.rvFav?.adapter = null
        _binding = null
    }
}