package com.sample.fruitmarket.remoteDb

import com.google.gson.Gson
import com.sample.fruitmarket.model.RegisteredSeller
import com.sample.fruitmarket.model.VillageRate

object MarketDB {

    fun getTodayRates() : String{
        return Gson().toJson(todayRate)
    }

    fun getSeller(id : String) : String {

        val seller: RegisteredSeller? = registeredSeller.stream()
            .filter { seller -> id == seller.loyaltyId }
            .findAny()
            .orElse(null)
        seller?.let {
            return Gson().toJson(seller)
        }
        return ""
    }

     val todayRate = arrayListOf(
                VillageRate(100, "Bagalkot", 110.00f),
                VillageRate(101, "Bagalkot", 110.00f),
                VillageRate(102, "Belgaum", 90.20f),
                VillageRate(103, "Chamarajnagar", 94.80f),
                VillageRate(104, "Davanagere", 99.00f),
                VillageRate(105, "Gulbarga", 105.30f),
                VillageRate(106, "Gadag", 100.00f),
                VillageRate(107, "Hassan", 112.50f),
                VillageRate(108, "Udupi", 88.00f),
                VillageRate(109, "Yadgir", 107.50f))

     val registeredSeller = arrayListOf(

                RegisteredSeller("1100", "Srini"),
                RegisteredSeller("1201", "Prasad"),
                RegisteredSeller("1001", "Sara"),
                RegisteredSeller("1002", "Rana"),
                RegisteredSeller("1003", "Raman"),
                RegisteredSeller("1004", "Subram"),
                RegisteredSeller("1005", "Mehta"),
                RegisteredSeller("1006", "Subramani"),
                RegisteredSeller("1007", "Sethi"),
                RegisteredSeller("1008", "Vijaya"),
                RegisteredSeller("1009", "Malik"),
                RegisteredSeller("1010", "Narayan"),
                RegisteredSeller("1011", "Mittal"),
                RegisteredSeller("1012", "Nita"),
                RegisteredSeller("1013", "Kishore"),
                RegisteredSeller("1014", "Roy"),
                RegisteredSeller("1015", "Chaudhary"),
                RegisteredSeller("1016", "CCharan"),
                RegisteredSeller("1017", "CNigam"),
                RegisteredSeller("1018", "CSen"),
                RegisteredSeller("1019", "CSubramanian"),
                RegisteredSeller("1020", "CNirmal"),
                RegisteredSeller("1021", "CPawan"),
                RegisteredSeller("1022", "CManohar"),
                RegisteredSeller("1023", "CSahni"),
                RegisteredSeller("1024", "CLalit"),
                RegisteredSeller("1025", "CRajan"),
                RegisteredSeller("1026", "CSehgal"),
                RegisteredSeller("1027", "CUddin"),
                RegisteredSeller("1028", "CSaini"))

}