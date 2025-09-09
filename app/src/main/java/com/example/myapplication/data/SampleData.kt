package com.example.myapplication.data

val SampleDaycares = listOf(
    Daycare(
        id = "1",
        name = "Happy Kids Daycare",
        rating = 4.6,
        distanceKm = 0.5,
        imageUrl = "https://fastly.picsum.photos/id/866/1000/500.jpg?hmac=FieISpYk6u8C4cQR-K4DNWuJcMUcidu0X0OntQA2mR4",
        facilities = listOf("Pickup", "Meals", "Indoor Play"),
        shortDescription = "Safe, fun, and caring environment",
        openHours = "Open, 9:00 am - 9:30 pm",
        address = "123 Kids Street, Suburb",
        pricePerDay = "₹500-800/day",
        latitude = 12.9716,
        longitude = 77.5946
    ),
    Daycare(
        id = "2",
        name = "Sunshine Creche",
        rating = 4.7,
        distanceKm = 0.9,
        imageUrl = "https://fastly.picsum.photos/id/28/4928/3264.jpg?hmac=GnYF-RnBUg44PFfU5pcw_Qs0ReOyStdnZ8MtQWJqTfA",
        facilities = listOf("Meals", "Outdoor Play"),
        shortDescription = "Creative activities for all ages",
        openHours = "Open, 8:00 am - 8:00 pm",
        address = "456 Bright Avenue, Downtown",
        pricePerDay = "₹600-900/day",
        latitude = 12.9755,
        longitude = 77.6050
    ),
    Daycare(
        id = "3",
        name = "Little Sprouts Nursery",
        rating = 4.3,
        distanceKm = 1.2,
        imageUrl = "https://images.unsplash.com/photo-1589998059171-988d887df646?w=800",
        facilities = listOf("Pickup", "Drop", "Music"),
        shortDescription = "Learning through play",
        openHours = "Open, 9:30 am - 7:00 pm",
        address = "789 Garden Lane, Eastside",
        pricePerDay = "₹350-550/day",
        latitude = 12.9800,
        longitude = 77.5900
    )
) 