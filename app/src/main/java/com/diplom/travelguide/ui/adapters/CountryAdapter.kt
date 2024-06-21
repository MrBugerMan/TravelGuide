package com.diplom.travelguide.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.diplom.travelguide.R
import com.diplom.travelguide.ui.adapters.data.CountryData
import com.diplom.travelguide.databinding.ItemCountryBinding

class CountryAdapter(
    private var mList: ArrayList<CountryData>
    //private var mList: ArrayList<CountriesAndInfoData>
    //private var countryInfo: ArrayList<CountriesAndInfoData>
) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(countryData: CountryData) { //  countryData: CountriesAndInfoData
            val flagURL = "https://flagsapi.com/${countryData.name}/shiny/64.png" // countryData.mainCountry.alpha2Code
            val country: TextView = itemView.findViewById(R.id.name_country)
            /*val id: TextView = itemView.findViewById(R.id.id_country)
            val iso2: TextView = itemView.findViewById(R.id.isio)*/
            val flag: ImageView = itemView.findViewById(R.id.flag)

            /*country.text = countryData.country
            id.text = countryData.id.toString()
            iso2.text = countryData.iso2*/
            country.text = countryData.name
            /*id.text = countryData.mainCountry.population.toString()
            iso2.text = countryData.mainCountry.alpha2Code*/
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
        fun onClick(position: Int, model: CountryData) // model: CountriesAndInfoData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: ArrayList<CountryData>) { // mList: ArrayList<CountriesAndInfoData>
        this.mList = mList as ArrayList<CountryData> //  as ArrayList<CountriesAndInfoData>
        notifyDataSetChanged()
    }

}