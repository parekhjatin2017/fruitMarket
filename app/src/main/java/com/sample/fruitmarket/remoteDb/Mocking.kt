package com.sample.fruitmarket.remoteDb

import okhttp3.*


object Mocking {

    fun getHttpMockClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(ResponseInterceptor())
            .build()
    }


    class ResponseInterceptor: Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var responseString  = ""
            responseString = if(chain.request().url().queryParameter("id")?.isNotEmpty() == true){
                val id = chain.request().url().queryParameter("id")
                MarketDB.getSeller(id!!)
            }else {
                MarketDB.getTodayRates()
            }
            val contentType = MediaType.parse("application/json; charset=utf-8")
            val body: ResponseBody = ResponseBody.create(contentType, responseString)

            return chain.proceed(chain.request())
                .newBuilder()
                .code( 200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(body)
                .addHeader("content-type", "application/json")
                .build()
        }
    }

}