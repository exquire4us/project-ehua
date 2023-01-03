package store.gadgetspace.security.token

data class TokenConfig(
    val tokenIssuer : String,
    val tokenAudience : String,
    val tokenSecret: String,
    val tokenExpirationDate: Long

)
