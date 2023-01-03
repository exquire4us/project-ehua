package store.gadgetspace.security.token

interface TokenService {
    fun generateToken(
        tokenConfig: TokenConfig,
        vararg claims: TokenClaims
    ) : String
}