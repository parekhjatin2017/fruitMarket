package com.sample.fruitmarket.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.sample.fruitmarket.Constants
import com.sample.fruitmarket.databinding.HomepageBinding
import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate
import com.sample.fruitmarket.viewModel.ApiStatus
import com.sample.fruitmarket.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: HomepageBinding
    private lateinit var rate : VillageRate
    private var seller: RegisteredSeller? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.getTodayRates(Constants.API_KEY)
        lifecycleScope.launchWhenCreated {
            registerStateFlow(mainViewModel)
        }
        binding.village.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                rate = binding.village.selectedItem as VillageRate
            }
        }
        binding.loyaltyId.doOnTextChanged { text, start, before, count ->  
            mainViewModel.getSeller(Constants.API_KEY, text.toString())
        }
        binding.weight.doOnTextChanged { text, start, before, count ->
           if(text?.isNotEmpty() == true){
               binding.total.text = calculateValue(rate, seller, text.toString().toInt()).toString()
               seller?.let { binding.loyaltyMessage.visibility = View.VISIBLE }
           }
        }

        binding.sellButton.setOnClickListener{
            if(!TextUtils.isEmpty(binding.name.text) && !TextUtils.isEmpty(binding.weight.text)){
                mainViewModel.sell()
                Toast.makeText(applicationContext, "Thank you ${binding.name.text}", Toast.LENGTH_SHORT).show()
                reInit()
            }
        }
    }

    private suspend fun registerStateFlow(mainViewModel: MainViewModel) {

        mainViewModel.apiStateFlow.collect { it ->

            when (it) {
                is ApiStatus.Loading -> {
                    seller = null
                }
                is ApiStatus.Failure -> {
                    
                }
                is ApiStatus.GetSeller -> {
                    it.data?.let {
                        binding.name.setText(it.name)
                        seller = it
                    }
                }
                is ApiStatus.GetRates -> {
                    setSpinner(it.data)
                }
                is ApiStatus.Idle -> {}
            }
        }

    }
    private fun setSpinner(villages : List<VillageRate>){
        binding.village.adapter =  ArrayAdapter(this, android.R.layout.simple_list_item_1, villages)
    }
    
    private fun calculateValue(village : VillageRate, seller: RegisteredSeller?, weightInTonnes : Int) : Float {
        var loyalty = 0.98f
        seller?.let { loyalty = 1.12F }
        return village.ratePerKg * loyalty * weightInTonnes * 1000
    }
    
    private fun reInit(){
        binding.loyaltyId.setText("")
        binding.name.setText("")
        binding.weight.setText("0")
        binding.loyaltyMessage.visibility = View.GONE
    }

}

