package com.sample.fruitmarket.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.fruitmarket.model.VillageRate
import com.sample.fruitmarket.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val marketRepository: MarketRepository) : ViewModel() {

    private val _apiStateFlow:MutableStateFlow<ApiStatus> = MutableStateFlow(ApiStatus.Idle)
    val apiStateFlow:StateFlow<ApiStatus> = _apiStateFlow

    private var  rateList : MutableStateFlow<List<VillageRate>?> = MutableStateFlow(null)

    fun getTodayRates(apiKey : String) = viewModelScope.launch {

        rateList.let {
            when (it.value) {
                null -> {
                    _apiStateFlow.value = ApiStatus.Loading
                    marketRepository.getTodayRates(apiKey)
                        .catch { e->
                            _apiStateFlow.value = ApiStatus.Failure(e)
                        }.collect { data->
                            rateList.value = data
                            _apiStateFlow.value = ApiStatus.GetRates(data)
                        }
                }else -> {
                    _apiStateFlow.value = ApiStatus.GetRates(it.value!!)
                }
            }
        }
    }

    fun getSeller(apiKey : String, id: String) = viewModelScope.launch {
        marketRepository.getSeller(apiKey, id)
            .catch { e->
                _apiStateFlow.value = ApiStatus.Failure(e)
            }.collect { data->
                _apiStateFlow.value = ApiStatus.GetSeller(data)
            }
    }

    fun sell(){
        // call api
    }
}