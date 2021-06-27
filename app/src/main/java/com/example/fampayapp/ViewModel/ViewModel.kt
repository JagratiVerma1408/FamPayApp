package com.example.fampayapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fampayapp.Model.ApiCalls
import com.example.fampayapp.Repository.Repo

class ViewModel : ViewModel() {

    fun getDataFromCard() : LiveData<ApiCalls> {
        return Repo.getDataFromCardGroups()
    }
}