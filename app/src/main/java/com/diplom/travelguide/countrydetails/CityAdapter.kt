package com.diplom.travelguide.countrydetails

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.travelguide.R

class CityAdapter(private var cityList: List<CityData>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private var onClickListener: CityAdapter.OnClickListener? = null
    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(cityData: CityData) {
            val city: TextView = itemView.findViewById(R.id.name_city)
            city.text = cityData.city
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cityList[position])

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, cityList[position])
            }
        }
    }


    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: CityData)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(cityList: List<CityData>) {
        this.cityList = cityList
        notifyDataSetChanged()
    }

}