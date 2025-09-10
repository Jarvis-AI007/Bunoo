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
    val longitude: Double,
    val about: String = "",
    val ageRange: String = "",
    val reviewCount: Int = 0,
    val reviews: List<Review> = emptyList()
)

data class Review(
    val id: String,
    val userName: String,
    val userAvatar: String,
    val rating: Double,
    val comment: String,
    val date: String
)

data class BookingDetails(
    val daycareId: String,
    val dateLabel: String,
    val pickTime: String,
    val dropTime: String,
    val pickAndDrop: Boolean,
    val price: Int
) 