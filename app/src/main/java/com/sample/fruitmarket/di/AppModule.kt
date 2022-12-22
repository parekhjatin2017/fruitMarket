package com.sample.fruitmarket.di

import com.sample.fruitmarket.remoteDb.Mocking
import com.sample.fruitmarket.repository.network.MarketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = "https://google.com/"

    @Provides
    @Singleton
    fun providesApiService(url:String) : MarketService =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Mocking.getHttpMockClient())
            .build()
            .create(MarketService::class.java)
}