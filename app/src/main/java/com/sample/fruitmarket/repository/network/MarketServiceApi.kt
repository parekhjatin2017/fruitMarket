package com.sample.fruitmarket.repository.network

import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate
import javax.inject.Inject

open class MarketServiceApi @Inject constructor(private val marketService: MarketService) {

    suspend fun getTodayRates(apiKey: String) : List<VillageRate> {
        return marketService.getTodayRates(apiKey)
    }

    suspend fun getSeller(apiKey: String, id: String) : RegisteredSeller? {
        return marketService.getSeller(apiKey, id)
    }
}