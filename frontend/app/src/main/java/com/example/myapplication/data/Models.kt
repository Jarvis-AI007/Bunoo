package com.example.myapplication.data

data class Daycare(
    val id: String,
    val name: String,
    val rating: Double,
    val distanceKm: Double,
    val imageUrl: String,
    val facilities: List<String>,
    val shortDescription: String,
    val openHours: String,
    val address: String,
    val pricePerDay: String,
    val latitude: Double,
    val longitude: Double
)

data class BookingDetails(
    val daycareId: String,
    val dateLabel: String,
    val pickTime: String,
    val dropTime: String,
    val pickAndDrop: Boolean,
    val price: Int
) 