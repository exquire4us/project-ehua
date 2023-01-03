package store.gadgetspace

import io.ktor.server.application.*
import store.gadgetspace.plugins.*
import store.gadgetspace.security.token.TokenConfig
import store.gadgetspace.security.token.TokenServiceImpl

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val tokenService = TokenServiceImpl()
    val tokenConfig = TokenConfig(
        tokenAudience = environment.config.property("jwt.audience").getString(),
        tokenExpirationDate = 365L * 1000L * 60L * 24L,
        tokenIssuer = environment.config.property("jwt.issuer").getString(),
        tokenSecret = System.getenv("JWT_SECRET")
    )

    configureSerialization()
    configureSecurity(tokenConfig)
    configureMonitoring()
    configureRouting(tokenService, tokenConfig)
}
