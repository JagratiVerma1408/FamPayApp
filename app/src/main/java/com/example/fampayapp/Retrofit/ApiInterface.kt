package com.example.fampayapp.Retrofit

import com.example.fampayapp.Model.ApiCalls
import com.example.fampayapp.Utils.Util.ENDPOINT
import retrofit2.Call
import retrofit2.http.GET
interface ApiInterface {
    @GET(ENDPOINT)
    fun getDataCard() : Call<ApiCalls>
}