package com.gs.nasa.favourite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gs.nasa.databinding.ActivityFavouriteBinding
import com.gs.nasa.pod.data.model.Data
import com.gs.nasa.pod.utils.Constants.FAVOURITE_PICTURES
import java.lang.reflect.Type


class FavouriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouriteBinding
    private val adapter = FavouriteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.adapter = adapter
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        val favPictures = intent.getStringExtra(FAVOURITE_PICTURES)
        val type: Type = object : TypeToken<List<Data?>?>() {}.type
        val prodList: List<Data> = Gson().fromJson(favPictures, type)
        adapter.setReviews(prodList)
    }
}