package com.example.centsoapp.models


import java.util.*

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var amount: Double,
    var category: String,
    var date: Long,
    var type: String )