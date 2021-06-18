package com.example.auabnb

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class ApartmentAdapter(private val apartments : List<Apartment>, private val context: Context) : BaseAdapter() {

    private lateinit var layoutInflater : LayoutInflater

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var _convertView = convertView
        var viewHolder : ApartmentAdapter.ViewHolder? = null

        if(viewHolder == null) {
            _convertView = layoutInflater.inflate(R.layout.row_apartment_item, null)
            viewHolder = ApartmentAdapter.ViewHolder(_convertView)
            convertView?.tag = viewHolder
        } else {
            viewHolder = convertView?.tag as ApartmentAdapter.ViewHolder
        }

        //set text to viewHolder
        viewHolder.apartmentNameTextView.text = apartments[position].getTitle()

        //set the image using Glide
        (Glide.with(context).load(apartments[position].getImage())
            .centerCrop())
            .placeholder(R.drawable.loadinggif)
            .into(viewHolder.apartmentImageView)

        //set the price of the apartment
        viewHolder.apartmentPriceTextView.text = apartments[position].getPrice().toString() + " тг"

        return _convertView
    }

    override fun getItem(position: Int): Any {
        return apartments[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return apartments.size
    }

    private class ViewHolder(view : View) {
        val apartmentImageView : ImageView = view.findViewById(R.id.imageViewApartment)
        val apartmentNameTextView : TextView = view.findViewById(R.id.apartmentTitleTextView)
        val apartmentPriceTextView : TextView = view.findViewById(R.id.apartmentPriceTextView)
    }

}