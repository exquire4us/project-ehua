package store.gadgetspace.plugins

import io.ktor.server.auth.*
import io.ktor.util.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import store.gadgetspace.security.token.TokenConfig

fun Application.configureSecurity(tokenConfig: TokenConfig) {
    
    authentication {
            jwt {
                realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
                verifier(
                    JWT
                        .require(Algorithm.HMAC256(tokenConfig.tokenSecret))
                        .withAudience(tokenConfig.tokenAudience)
                        .withIssuer(tokenConfig.tokenIssuer)
                        .build()
                )
                validate { credential ->
                    if (credential.payload.audience.contains(tokenConfig.tokenAudience)) JWTPrincipal(credential.payload) else null
                }
            }
        }

}
