package com.example.myapplication.data

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val address: String,
    val memberSince: String,
    val bookingsCompleted: Int,
    val avatarUrl: String = ""
)

data class Child(
    val id: String,
    val name: String,
    val age: String,
    val allergies: String = "",
    val notes: String = "",
    val avatarUrl: String = ""
)

val SampleDaycares = listOf(
    Daycare(
        id = "1",
        name = "Happy Kids Daycare",
        rating = 4.8,
        distanceKm = 0.5,
        imageUrl = "file:///android_asset/day1.jpg",
        facilities = listOf("Pickup", "Meals", "Indoor Play"),
        shortDescription = "Safe, fun, and caring environment",
        openHours = "Open, 9:00 am - 9:30 pm",
        address = "123 Kids Street, Suburb",
        pricePerDay = "₹500-800/day",
        latitude = 12.9716,
        longitude = 77.5946,
        about = "A delicious home, we met set stations supplies talented bulletin amazing persons ubicitor idos station changes involved team.",
        ageRange = "6 months - 5 years",
        reviewCount = 560,
        reviews = listOf(
            Review(
                id = "1",
                userName = "Priya Sharma",
                userAvatar = "https://i.pravatar.cc/150?img=1",
                rating = 5.0,
                comment = "Great service for large family, amazing staff and the environment is also good",
                date = "2 days ago"
            ),
            Review(
                id = "2",
                userName = "Rajesh Kumar",
                userAvatar = "https://i.pravatar.cc/150?img=2",
                rating = 4.5,
                comment = "Very clean and safe environment. My child loves going there.",
                date = "1 week ago"
            )
        )
    ),
    Daycare(
        id = "2",
        name = "Sunshine Creche",
        rating = 4.7,
        distanceKm = 0.9,
        imageUrl = "file:///android_asset/day2.jpg",
        facilities = listOf("Meals", "Outdoor Play"),
        shortDescription = "Creative activities for all ages",
        openHours = "Open, 8:00 am - 8:00 pm",
        address = "456 Bright Avenue, Downtown",
        pricePerDay = "₹600-900/day",
        latitude = 12.9755,
        longitude = 77.6050,
        about = "A wonderful place where children learn through creative activities and outdoor play. Our experienced staff ensures a nurturing environment.",
        ageRange = "1 year - 6 years",
        reviewCount = 320,
        reviews = listOf(
            Review(
                id = "3",
                userName = "Anita Singh",
                userAvatar = "https://i.pravatar.cc/150?img=3",
                rating = 4.8,
                comment = "Excellent facilities and caring staff. Highly recommended!",
                date = "3 days ago"
            )
        )
    ),
    Daycare(
        id = "3",
        name = "Little Sprouts Nursery",
        rating = 4.3,
        distanceKm = 1.2,
        imageUrl = "file:///android_asset/day3.jpg",
        facilities = listOf("Pickup", "Drop", "Music"),
        shortDescription = "Learning through play",
        openHours = "Open, 9:30 am - 7:00 pm",
        address = "789 Garden Lane, Eastside",
        pricePerDay = "₹350-550/day",
        latitude = 12.9800,
        longitude = 77.5900,
        about = "We focus on learning through play with music and creative activities. Our small group sizes ensure individual attention.",
        ageRange = "8 months - 4 years",
        reviewCount = 180,
        reviews = listOf(
            Review(
                id = "4",
                userName = "Suresh Patel",
                userAvatar = "https://i.pravatar.cc/150?img=4",
                rating = 4.2,
                comment = "Good value for money. My daughter enjoys the music classes.",
                date = "1 week ago"
            )
        )
    )
)

val SampleUser = User(
    id = "user1",
    name = "Sarah Johnson",
    email = "sarah.johnson@email.com",
    phone = "+91 98765 43210",
    address = "Sector 12, Noida, UP",
    memberSince = "January 2024",
    bookingsCompleted = 15,
    avatarUrl = "https://images.unsplash.com/photo-1494790108755-2616b612b786?w=150"
)

val SampleChildren = listOf(
    Child(
        id = "child1",
        name = "Emma",
        age = "3 years",
        allergies = "Nuts",
        notes = "Loves drawing and playing with blocks",
        avatarUrl = "https://images.unsplash.com/photo-1503919545889-aef636e10ad4?w=150"
    ),
    Child(
        id = "child2",
        name = "Liam",
        age = "5 years",
        allergies = "",
        notes = "Very active, enjoys outdoor activities",
        avatarUrl = "https://images.unsplash.com/photo-1519340241574-2cec6aef0c01?w=150"
    )
) 