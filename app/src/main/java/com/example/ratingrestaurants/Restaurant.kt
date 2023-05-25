package com.example.ratingrestaurants

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Restaurant (
    var name : String = "Someplace",
    var rating : Double = 0.00,
    var priceRange : String = "$",
    var location : String = "South Pasadena, CA",
    var desc : String = "American",
    var ownerId: String? = null,
    var objectId: String? = null
    ) : Parcelable {}