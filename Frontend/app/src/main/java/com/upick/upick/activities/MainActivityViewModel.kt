package com.upick.upick.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private val repository = MainRepository

//    private val _counter = MutableLiveData(1)
//    val counter: LiveData<Int>
//        get() = _counter

    init {
        viewModelScope.launch {
            delay(3000L)
//            _counter.value = 100

            delay(3000L)
//            _counter.value = 1000

//            delay(3000L)
//            MainRepository.makeGETRequest(1)
        }
    }
}