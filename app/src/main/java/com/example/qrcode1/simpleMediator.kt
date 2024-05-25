package com.example.qrcode1

class simpleMediator {
    private var fName = ""
    private var lName = ""
    private var pNumber = ""
    private var allData = ""

    fun setFirstName(firstName: String) {
        this.fName = firstName
        updateAllData()
    }

    fun setLastName(lastName: String) {
        this.lName = lastName
        updateAllData()
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.pNumber = phoneNumber
        updateAllData()
    }

    private fun updateAllData() {
        this.allData = "$fName +-+ $lName +-+ $pNumber".trim()
    }

    fun getAllData(): String {
        return allData
    }

    fun test() {
        println(allData)
    }

}