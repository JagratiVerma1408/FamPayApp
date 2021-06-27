package com.example.fampayapp.Repository


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fampayapp.Model.ApiCalls
import com.example.fampayapp.Retrofit.RetrofitService
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback


object Repo {
    fun getDataFromCardGroups() : MutableLiveData<ApiCalls>
    {
        val mutableLiveData : MutableLiveData<ApiCalls> = MutableLiveData()
        val calling = RetrofitService.apiInterface.getDataCard()
        calling.enqueue(object : Callback<ApiCalls> {
            override fun onResponse(call: Call<ApiCalls>, response: Response<ApiCalls>) {
                if(response.isSuccessful)
                {
                    Log.d("data", "data is  :${response.body()}")
                    mutableLiveData.value=response.body()

                }
                else mutableLiveData.value=null
            }

            override fun onFailure(call:Call<ApiCalls>, t: Throwable) {
                mutableLiveData.value = null
            }

        })
        return mutableLiveData
    }

}