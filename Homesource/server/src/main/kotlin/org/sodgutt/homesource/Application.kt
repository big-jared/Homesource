package org.sodgutt.homesource

import Greeting
import Profile
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.sessions.*
import org.jetbrains.exposed.sql.and
import org.sodgutt.homesource.Db.dbQuery
import org.jetbrains.exposed.sql.select

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::main)
        .start(wait = true)
}

fun Application.main() {
    install(Compression)
    install(DefaultHeaders)
    install(CallLogging)
    install(Sessions) {
        cookie<Profile>("KTSESSION", storage = SessionStorageMemory()) {
            cookie.path = "/"
            cookie.extensions["SameSite"] = "strict"
        }
    }
    Db.init(environment.config)

    install(Authentication) {
        form {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                dbQuery {
                    UserDao.select {
                        (UserDao.username eq credentials.name) and (UserDao.password eq DigestUtils.sha256Hex(
                            credentials.password
                        ))
                    }.firstOrNull()?.let {
                        UserIdPrincipal(credentials.name)
                    }
                }
            }
            skipWhen { call -> call.sessions.get<Profile>() != null }
        }
    }
    routing {
        get("/") {
            call.respondText("Ktor: ${UserDao.name.table.tableName}")
        }
    }
}