package com.diplom.travelguide.countries

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diplom.travelguide.R
import com.diplom.travelguide.databinding.EachItemCountryBinding

class CountryAdapter(
    private var mList: ArrayList<CountryData>
) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var onClickListener: OnClickListener? = null

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*val flag: ImageView = itemView.findViewById(R.id.flag)
        val country: TextView = itemView.findViewById(R.id.name_country)*/
        fun bind(countryData: CountryData) {
            //val flag: ImageView = itemView.findViewById(R.id.flag)
            val country: TextView = itemView.findViewById(R.id.name_country)
            val id: TextView = itemView.findViewById(R.id.id_country)
            val iso2: TextView = itemView.findViewById(R.id.isio)
            //flag.setImageResource(countryData.flag)  // старое решение
            country.text = countryData.country
            id.text = countryData.id.toString()
            iso2.text = countryData.iso2
            //Glide.with(flag).load(countryData.flag).into(flag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(
            EachItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        /*holder.flag.setImageResource(mList[position].flag)
        holder.country.text = mList[position].country*/
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
        fun onClick(position: Int, model: CountryData)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: List<CountryData>) {
        this.mList = mList as ArrayList<CountryData>
        notifyDataSetChanged()
    }

}