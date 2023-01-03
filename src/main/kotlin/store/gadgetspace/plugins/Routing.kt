package store.gadgetspace.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import store.gadgetspace.models.User
import store.gadgetspace.models.request.LoginRequest
import store.gadgetspace.models.request.SignupRequest
import store.gadgetspace.models.response.ApiResponse
import store.gadgetspace.models.response.LoginResponse
import store.gadgetspace.security.token.TokenClaims
import store.gadgetspace.security.token.TokenConfig
import store.gadgetspace.security.token.TokenService
import store.gadgetspace.storage.DataSource
import store.gadgetspace.storage.UserDataStorage
import kotlin.random.Random

fun Application.configureRouting(tokenService: TokenService, tokenConfig: TokenConfig) {
    routing {
        signUp()
        login(tokenService, tokenConfig)
        users()
    }

}

private fun Route.signUp() {


    post("/signup") {
        val userDetails: SignupRequest = call.receiveNullable<SignupRequest>() ?: kotlin.run {
            call.respondText("null null ", status = HttpStatusCode.OK)
            return@post
        }

        val areFieldsBlank =
            userDetails.name.isNullOrBlank() || userDetails.email.isNullOrBlank() || userDetails.phoneNumber.isNullOrBlank() || userDetails.password.isNullOrBlank()

        val isPasswordShort = if (userDetails.password.length < 8) true else false

        if (areFieldsBlank || isPasswordShort) {
            call.respondText("parameters cannot be blank or null ", status = HttpStatusCode.BadRequest)
            return@post
        }

        val user = User(
            id = Random.nextInt(),
            name = userDetails.name,
            email = userDetails.email,
            password = userDetails.password,
            phoneNumber = userDetails.phoneNumber
        )
        DataSource.storageService.addUser(user)
        call.respondText("${userDetails.name} has been successfully created")


    }

}

private fun Route.login(tokenService: TokenService, tokenConfig: TokenConfig) {
    post ("/login") {
        val request: LoginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respondText("Invalid Auth Details ", status = HttpStatusCode.Conflict)
            return@post
        }

        val user = DataSource.storageService.findUserByEmail(request.email)
            ?: kotlin.run {
                call.respond(message = "User does not exist in database ", status = HttpStatusCode.BadRequest)
                return@post
            }

        val isUserValid = DataSource.validateUser(
            user = user,
            email = request.email,
            password = request.password
        )

        if (!isUserValid) {
            call.respond(HttpStatusCode.Conflict, "Invalid password ")
            return@post
        }

        val token = tokenService.generateToken(tokenConfig, TokenClaims(
            name = "userName",
            value = user.name
        ))

        call.respond(LoginResponse(
            message = "Success",
            token = token
        ))
    }
}

private fun Route.users() {
    authenticate {
        get("/users"){
            val users = DataSource.storageService.getAllUsers()
            val response = ApiResponse(
                message = "List of all users",
                success = true,
                data = users
            )

            call.respond(HttpStatusCode.OK,response)
        }
    }
}



