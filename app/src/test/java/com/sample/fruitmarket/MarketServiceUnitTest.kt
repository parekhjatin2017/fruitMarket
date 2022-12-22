package com.sample.fruitmarket

import com.sample.fruitmarket.repository.MarketRepository
import com.sample.fruitmarket.repository.network.MarketServiceApi
import com.sample.fruitmarket.viewModel.ApiStatus
import com.sample.fruitmarket.viewModel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MarketServiceUnitTest {

    lateinit var  viewmodel : MainViewModel

    @Before
    fun init(){
        val repository = MarketRepository(MarketServiceApi(MockMarketService()))
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewmodel = MainViewModel(repository)
    }

    @Test
    fun testRates() = runTest {
        viewmodel.getTodayRates("api_key")
        launch {  viewmodel.apiStateFlow.collect() {
            when (it) {
                is ApiStatus.GetRates -> {
                    assert(it.data.isNotEmpty())
                    cancel()
                }
                else -> {}
            }
        }}
    }

    @Test
    fun testRegisterSeller() = runTest {
        viewmodel.getSeller("api_key", "1011")

        launch {  viewmodel.apiStateFlow.collect() { apiStatus ->
            when (apiStatus) {
                is ApiStatus.GetSeller -> {
                    apiStatus.data?.let {
                        assert(it.loyaltyId == "1011" && it.name == "Mittal" )
                        cancel()
                    }
                }
                else -> {}
            }
        }}
    }

    @Test
    fun testUnRegisterSeller() = runTest {
        viewmodel.getSeller("api_key", "2011")

        launch {  viewmodel.apiStateFlow.collect() { apiStatus ->
            when (apiStatus) {
                is ApiStatus.GetSeller -> {
                    assert(apiStatus.data == null)
                    cancel()
                }
                else -> {}
            }
        }}
    }
}