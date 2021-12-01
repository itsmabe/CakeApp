package com.me.cakeapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.cakeapp.R
import com.me.cakeapp.api.CakesAPI
import com.me.cakeapp.data.mapper.CakeMapper
import com.me.cakeapp.model.Cake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mapper: CakeMapper

    private var cakesLD = MutableLiveData<ArrayList<Cake>>()
    private var errorLD = MutableLiveData<Int>()

    fun getCakesLD() = cakesLD
    fun getErrorLD() = errorLD

    fun getCakes() = viewModelScope.launch {
        try {
            cakesLD.postValue(
                mapper.toCakesList(
                    CakesAPI.create().getCakes()
                )
            )
        } catch (e: Exception) {
            errorLD.postValue(R.string.error_message)
        }
    }
}