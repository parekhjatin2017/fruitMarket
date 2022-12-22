package com.sample.fruitmarket.model

data class VillageRate(val id : Int, val name : String, val ratePerKg : Float){

    override fun toString(): String {
        return "$name   $ratePerKg/ kg"
    }
}
