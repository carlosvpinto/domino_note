package com.carlosvpinto.anotardomino.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class SharedViewModel : ViewModel() {
    // Datos que deseas persistir
    var editTextValues1: Array<String?> = arrayOfNulls(15)
    var editTextValues2: Array<String?> = arrayOfNulls(15)
    var linearLayoutVisibilities: BooleanArray = booleanArrayOf()
}