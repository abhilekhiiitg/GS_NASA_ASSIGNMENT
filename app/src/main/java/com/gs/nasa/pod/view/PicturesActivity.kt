package com.gs.nasa.pod.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.gs.nasa.NasaPotdApplication
import com.gs.nasa.R
import com.gs.nasa.databinding.ActivityMainBinding
import com.gs.nasa.favourite.FavouriteActivity
import com.gs.nasa.pod.data.model.Data
import com.gs.nasa.pod.di.component.DaggerPicturesActivityComponent
import com.gs.nasa.pod.utils.Constants.FAVOURITE_PICTURES
import com.gs.nasa.pod.viewmodel.PicturesViewModel
import com.gs.nasa.pod.viewmodel.PicturesViewModelFactory
import com.gs.nasa.pod.viewmodel.model.PotdViewState
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class PicturesActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: PicturesViewModelFactory
    private lateinit var mainViewModel: PicturesViewModel
    private lateinit var binding: ActivityMainBinding
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupDagger()
        setUpDatePicker()
        setUpFavouriteClick()

        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[PicturesViewModel::class.java]
        mainViewModel.fetchPictureOfTheDay(getTimeInFormat(System.currentTimeMillis()))
        mainViewModel.viewStateLiveData.observe(this) {
            when (it) {
                is PotdViewState.Success<*> -> handleSuccessState(it.data)
                is PotdViewState.Loading -> handleLoading()
                is PotdViewState.Error -> handleError(it.error)
            }
        }
    }

    private fun setUpDatePicker() {
        binding.datePickerBtn.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val datePicker = builder.build()
            datePicker.addOnPositiveButtonClickListener {
                mainViewModel.fetchPictureOfTheDay(getTimeInFormat(it))
            }
            datePicker.show(supportFragmentManager, getString(R.string.POTD_SCREEN))
        }
    }

    private fun setUpFavouriteClick() {
        binding.favBtn.setOnClickListener {
            mainViewModel.fetchFavouritePictures()
        }
    }

    private fun getTimeInFormat(time: Long): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.time = Date(time)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val formattedDay = if (calendar.get(Calendar.DAY_OF_MONTH) < 10) "0$day" else day
        return "${year}-${month}-${formattedDay}"
    }

    private fun setupDagger() {
        DaggerPicturesActivityComponent.builder()
            .appComponent((application as NasaPotdApplication).appComponent)
            .build()
            .inject(this)
    }

    private fun handleSuccessState(data: Any?) {
        stopShimmerEffect()
        when (data) {
            is Data -> setPictureOfTheDayView(data)
            is List<*> -> redirectToFavourites(data as List<Data>)
        }
    }

    private fun setPictureOfTheDayView(data: Data) {
        binding.cardView.visibility = View.VISIBLE
        binding.header.visibility = View.VISIBLE
        binding.date.text = data.date
        binding.title.text = data.title
        binding.description.text = data.explanation
        isFavourite = data.isFavourite
        binding.favImage.pauseAnimation()

        binding.favImage.progress = if (data.isFavourite) 1.0f else 0.0f

        Glide.with(this).load(data.url).placeholder(R.drawable.ic_download)
            .error(R.drawable.ic_download).into(binding.image)

        binding.favImage.setOnClickListener {
            isFavourite = !isFavourite
            if (isFavourite)
                binding.favImage.playAnimation()
            else {
                binding.favImage.progress = 0.0f
                binding.favImage.pauseAnimation()
            }
            mainViewModel.updateFavouriteStatus(isFavourite, data.url)
        }
    }

    private fun handleLoading() {
        startShimmerEffect()
    }

    private fun handleError(error: String?) {
        val msg = error ?: getString(R.string.please_try_after_some_time)
        stopShimmerEffect()
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }

    private fun stopShimmerEffect() {
        binding.shimmerViewContainer.stopShimmerAnimation();
        binding.shimmerViewContainer.visibility = View.GONE;
    }

    private fun startShimmerEffect() {
        binding.shimmerViewContainer.startShimmerAnimation();
        binding.shimmerViewContainer.visibility = View.VISIBLE;
    }

    private fun redirectToFavourites(data: List<Data>) {
        val intent = Intent(this, FavouriteActivity::class.java)
        intent.putExtra(FAVOURITE_PICTURES, Gson().toJson(data))
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up)
    }
}
