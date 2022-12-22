package com.sample.fruitmarket

import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate
import com.sample.fruitmarket.remoteDb.MarketDB
import com.sample.fruitmarket.repository.network.MarketService

class MockMarketService() : MarketService {

    override suspend fun getTodayRates(apiKey: String): List<VillageRate> {
        return MarketDB.todayRate
    }

    override suspend fun getSeller(apiKey: String, id: String): RegisteredSeller? {

        return  MarketDB.registeredSeller.stream()
            .filter { seller -> id == seller.loyaltyId }
            .findAny()
            .orElse(null)
    }

}