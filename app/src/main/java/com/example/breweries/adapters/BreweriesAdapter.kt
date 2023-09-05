package com.example.calatour.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.calatour.R
import com.example.calatour.models.BreweryByCity

class BreweriesAdapter(
    private val context: Context,
    private val dataSource: List<BreweryByCity>): BaseAdapter()  {
    private lateinit var brewery:BreweryByCity;
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0];
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong();
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = convertView ?: inflater.inflate(
            R.layout.brewery_list_item,
            parent, false)
        val breweryByCity = getItem(position) as BreweryByCity

        rowView.findViewById<TextView>(R.id.brewery_list_item_name).text = breweryByCity.name
        rowView.findViewById<TextView>(R.id.brewery_list_item_type).text = breweryByCity.brewery_type
        rowView.findViewById<TextView>(R.id.brewery_list_item_street).text = breweryByCity.street
        rowView.findViewById<TextView>(R.id.brewery_list_item_city).text = breweryByCity.city
        rowView.findViewById<TextView>(R.id.brewery_list_item_state).text = breweryByCity.state
        rowView.findViewById<TextView>(R.id.brewery_list_item_country).text = breweryByCity.country
        rowView.findViewById<TextView>(R.id.brewery_list_item_phone).text = breweryByCity.phone
        rowView.findViewById<TextView>(R.id.brewery_list_item_websiteurl).text = breweryByCity.website_url
        var isFav = rowView.findViewById<ImageView>(R.id.brewery_fav_image)

        if (!breweryByCity.isFav) {
            isFav.visibility = View.INVISIBLE
            rowView.findViewById<TextView>(R.id.brewery_list_item_name).setTextColor(Color.parseColor("#000000"))
        }
        else {
            isFav.visibility = View.VISIBLE
            rowView.findViewById<TextView>(R.id.brewery_list_item_name).setTextColor(Color.parseColor("#5600e3"))
        }

        return rowView
    }
}