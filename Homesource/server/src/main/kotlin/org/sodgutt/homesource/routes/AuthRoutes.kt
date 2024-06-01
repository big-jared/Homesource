package org.sodgutt.homesource.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.sodgutt.homesource.services.UserExistsSignupException
import org.sodgutt.homesource.services.UserService
import java.lang.Exception

fun Routing.auth() {
    val service by inject<UserService>()

    post("/user/login") {
        try {
            service.login(call.receive())?.let { user ->
                call.respond(HttpStatusCode.OK, user)
            } ?: call.respond(HttpStatusCode.BadRequest, "Login info is incorrect")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error occured")
        }
    }
    
    post("user/signup") {
        try {
            call.respond(HttpStatusCode.Created, mapOf("token" to service.signUp(call.receive())))
        } catch (e: UserExistsSignupException) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Error occured")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error occured")
        }
    }
    
    get("user/doSomething") {
        try {
            call.respond(HttpStatusCode.OK, "HI JARED")
        } catch (e: UserExistsSignupException) {
            call.respond(HttpStatusCode.Conflict, e.message ?: "Error occured")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, e.message ?: "Error occured")
        }
    }
}