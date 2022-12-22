package com.sample.fruitmarket.viewModel

import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate


sealed class ApiStatus{
    object Idle : ApiStatus()
    object Loading : ApiStatus()
    class GetRates(val data : List<VillageRate>) : ApiStatus()
    class GetSeller(val data : RegisteredSeller?) : ApiStatus()
    class Failure(val msg:Throwable) : ApiStatus()
}
