package com.example.calatour.models

data class BreweryByCity (
    val id: String,
    val name: String,
    val brewery_type: String,
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val phone: String?,
    val website_url: String?,
    var isFav: Boolean = false
):java.io.Serializable

