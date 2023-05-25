package com.example.loginandregistration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Loan(
    var name: String = "Someone",
    var reason : String = "Lunch",
    var amount : Double = 5.00,
    var dateLent : Date? = Date(1678726065128),
    var amountRepaid : Double = 0.00,
    var dateRepayed : Date? = null,
    var isItFullyRepaid : Boolean = false,
    var ownerId: String? = null,
    var objectId: String? = null
    ) : Parcelable {
    fun balanceRemaining() : Double {
        return amount - amountRepaid
    }
}
