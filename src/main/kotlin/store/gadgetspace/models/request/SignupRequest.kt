package store.gadgetspace.models.request

data class SignupRequest(
    val name: String,
    val email: String,
    val password: String,
    val phoneNumber: String,

)
