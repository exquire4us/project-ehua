package store.gadgetspace.models


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
)
