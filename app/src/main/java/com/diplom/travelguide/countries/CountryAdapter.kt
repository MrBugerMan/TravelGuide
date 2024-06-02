package com.diplom.travelguide.countries

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplom.travelguide.CountriesAndInfoData
import com.diplom.travelguide.R
import com.diplom.travelguide.databinding.ItemCountryBinding

class CountryAdapter(
    //private var mList: ArrayList<CountryData>
    private var mList: ArrayList<CountriesAndInfoData>
    //private var countryInfo: ArrayList<CountriesAndInfoData>
) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(countryData: CountriesAndInfoData) { //  countryData: CountryData
            val flagURL = "https://flagsapi.com/${countryData.mainCountry.alpha2Code}/shiny/64.png" // countryData.mainCountry.alpha2Code
            val country: TextView = itemView.findViewById(R.id.name_country)
            val id: TextView = itemView.findViewById(R.id.id_country)
            val iso2: TextView = itemView.findViewById(R.id.isio)
            val flag: ImageView = itemView.findViewById(R.id.flag)

            /*country.text = countryData.country
            id.text = countryData.id.toString()
            iso2.text = countryData.iso2*/
            country.text = countryData.mainCountry.name
            id.text = countryData.mainCountry.population.toString()
            iso2.text = countryData.mainCountry.alpha2Code
            Glide.with(flag.context).load(flagURL).diskCacheStrategy(DiskCacheStrategy.ALL).into(flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(mList[position])

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, mList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: CountriesAndInfoData) // model: CountryData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: ArrayList<CountriesAndInfoData>) { // mList: ArrayList<CountryData>
        this.mList = mList as ArrayList<CountriesAndInfoData> //  as ArrayList<CountryData>
        notifyDataSetChanged()
    }

}