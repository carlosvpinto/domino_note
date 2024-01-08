package com.carlosvpinto.anotardomino.ui.configuracion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is fragment Settting"
    }
    val text: LiveData<String> = _text
}