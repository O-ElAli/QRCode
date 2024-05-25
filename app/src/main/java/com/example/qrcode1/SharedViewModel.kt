package com.example.qrcode1

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MediatorLiveData

class SharedViewModel : ViewModel() {
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()

    // Combined data as LiveData
    val combinedData = MediatorLiveData<String>().apply {
        addSource(firstName) { value = formatData() }
        addSource(lastName) { value = formatData() }
        addSource(phoneNumber) { value = formatData() }
    }

    fun formatData(): String {
        return "${firstName.value ?: ""} +-+ ${lastName.value ?: ""} +-+ ${phoneNumber.value ?: ""}".trim()
    }
}
