package com.defvest.devfestnorth.models

data class User(var uuid: String, var fullName: String, var email: String, var imageUrl: String){
    constructor() : this("", "", "", "")
}