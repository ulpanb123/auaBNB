package com.example.auabnb

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.net.URI

class CityAdapter(private val cities : List<City>, private val context: Context) : BaseAdapter() {

    private lateinit var layoutInflater : LayoutInflater


    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var _convertView = convertView
        var viewHolder : ViewHolder? = null

        if(viewHolder == null) {
            _convertView = layoutInflater.inflate(R.layout.row_city_item, null)
            viewHolder = ViewHolder(_convertView)
            convertView?.tag = viewHolder
        } else {
            viewHolder = convertView?.tag as ViewHolder
        }

        //set text to viewHolder
        viewHolder.cityTextView.text = cities[position].getTitle()

        //set the image using Glide
        (Glide.with(context).load(cities[position].getImage()).centerCrop()).into(viewHolder.cityImageView)


        return _convertView
    }

    override fun getItem(position: Int): Any {
        return cities[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return cities.size
    }

    private class ViewHolder(view : View) {
        val cityImageView : ImageView = view.findViewById(R.id.cityImageView)
        val cityTextView : TextView = view.findViewById(R.id.cityTitleTextView)
    }

}


