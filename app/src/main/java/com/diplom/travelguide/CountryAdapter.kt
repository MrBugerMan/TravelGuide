package com.diplom.travelguide

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.travelguide.databinding.EachItemCountryBinding

class CountryAdapter(
    private var mList: ArrayList<CountryData>
) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private var onClickListener: OnClickListener? = null

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
        holder.flag.setImageResource(mList[position].flag)
        holder.country.text = mList[position].title

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, mList[position].title)
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
        fun onClick(position: Int, title: String)
    }

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flag: ImageView = itemView.findViewById(R.id.flag)
        val country: TextView = itemView.findViewById(R.id.name_country)
    }

    @SuppressLint("NotifyDataSetChanged") // ??
    fun setFilteredList(mList: List<CountryData>) {
        this.mList = mList as ArrayList<CountryData>
        notifyDataSetChanged() // ??
    }

}