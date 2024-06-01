package org.sodgutt.homesource

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.sodgutt.homesource.routes.auth
import org.sodgutt.homesource.services.UserService
import org.sodgutt.homesource.services.UserServiceImpl

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::main)
        .start(wait = true)
}

fun Application.main() {
    install(Compression)
    install(DefaultHeaders)
    install(CallLogging)
    install(Koin) {
        module {
            single<UserService>(createdAtStart = true) {
                UserServiceImpl()
            }
        }
    }

    Db.init(environment.config)
    
    routing {
        auth()
    }
}