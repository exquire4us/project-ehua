package store.gadgetspace.plugins

import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import store.gadgetspace.models.User
import store.gadgetspace.models.request.SignupRequest

fun Application.configureRouting() {
    onboardingRouting()
}

private fun Application.onboardingRouting() {
    routing {
        get("/") {
            call.respond(object {
                val message = "Welcome to the project Ehua!"
            })
        }
        post("/login") {
            call.respondText("Logged in called")
        }

        post("/signup") {
            val userDetails = call.receive<SignupRequest>()
            try {

                require(
                    userDetails.name.isNotBlank()
                            && userDetails.email.isNotBlank()
                            && userDetails.password.isNotBlank()
                            && userDetails.phoneNumber.isNotBlank()
                )
                call.respondText("${userDetails.name} has been successfully created")

            } catch (e: IllegalArgumentException) {
                call.respondText("parameters cannot be blank or null ", status = HttpStatusCode.BadRequest)
            } catch (e: NullPointerException) {
                call.respondText("parameters cannot be blank or null ", status = HttpStatusCode.BadRequest)
            }


        }
    }
}


