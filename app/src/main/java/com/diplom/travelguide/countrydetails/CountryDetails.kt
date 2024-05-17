package com.diplom.travelguide.countrydetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.diplom.travelguide.countries.CountryData
import com.diplom.travelguide.countries.MainActivity
import com.diplom.travelguide.databinding.ActivityCountryDetailsBinding

class CountryDetails: AppCompatActivity() {
    private lateinit var binding: ActivityCountryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed()}

        var mList: CountryData?= null

        if(intent.hasExtra(MainActivity.NEXT_SCREEN)){
            mList = intent.getSerializableExtra(MainActivity.NEXT_SCREEN) as CountryData
        }

        if(mList != null){
            binding.nameCountry.text = mList.country
            //binding.flag.setImageResource(mList.flag)
            binding.toolbar.title = mList.country
        }

    }

}