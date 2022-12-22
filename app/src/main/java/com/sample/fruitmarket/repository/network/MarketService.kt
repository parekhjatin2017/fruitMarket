package com.sample.fruitmarket.repository.network

import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate
import retrofit2.http.GET
import retrofit2.http.Query

interface MarketService {

    @GET("/rates")
    suspend fun getTodayRates(@Query("api_key") apiKey: String): List<VillageRate>

    @GET("/seller")
    suspend fun getSeller(@Query("api_key") apiKey: String, @Query("id") id: String): RegisteredSeller?

}