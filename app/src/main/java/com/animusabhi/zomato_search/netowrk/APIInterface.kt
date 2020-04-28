package com.animusabhi.zomato_search.netowrk

import com.animusabhi.zomato_search.model.SearchModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIInterface {
    @Headers("user-key: 47efd9e28084554e8cb693354d4c997d")
    @GET("api/v2.1/search")
    fun getRestaurantList(
        @Query("entity_type") entity_type: String,
        @Query("q") query: String,
        @Query("start") startValue: String,
        @Query("lat") lat: Double,
        @Query("lon") long: Double
    ): Call<SearchModel>
}