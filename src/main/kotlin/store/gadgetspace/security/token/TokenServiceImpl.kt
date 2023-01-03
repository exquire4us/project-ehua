package store.gadgetspace.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class TokenServiceImpl : TokenService {
    override fun generateToken(tokenConfig: TokenConfig, vararg claims: TokenClaims): String {
        var token =  JWT.create()
            .withAudience(tokenConfig.tokenAudience)
            .withIssuer(tokenConfig.tokenIssuer)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.tokenExpirationDate))

        claims.forEach {
             token = token.withClaim(it.name, it.value)
        }
        return token.sign(Algorithm.HMAC256(tokenConfig.tokenSecret))
    }
}