package com.animusabhi.zomato_search.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.animusabhi.zomato_search.R
import com.animusabhi.zomato_search.model.Restaurant
import com.animusabhi.zomato_search.model.Restaurants
import com.bumptech.glide.Glide

class RestaurantListAdapter(
    val context: Activity,
    private val restaurantList: ArrayList<Restaurants>
) : RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(v)
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurants: Restaurants = restaurantList[position]
        val restaurant: Restaurant = restaurants.restaurant

        holder.resName.text = restaurant.name
        Glide.with(context)
            .load(restaurant.thumb)
            .placeholder(R.drawable.zomato)
            .error(R.drawable.zomato)
            .into(holder.placeImage);
        holder.place_description.text = restaurant.cuisines
        holder.place_budget.text =
            "Average cost for two : " + restaurant.average_cost_for_two
        holder.place_location.text =
            if (restaurant.location != null) restaurant.location.city else ""
    }


    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var resName = itemView.findViewById<TextView>(R.id.place_name)
        var placeImage = itemView.findViewById<ImageView>(R.id.place_image)
        var place_description = itemView.findViewById<TextView>(R.id.place_description)
        var place_budget = itemView.findViewById<TextView>(R.id.place_budget)
        var place_location = itemView.findViewById<TextView>(R.id.place_location)
    }
}