package com.spbarber

import com.spbarber.plugins.configureRouting
import com.spbarber.plugins.configureSerialization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

val port = System.getenv("PORT")?.toInt() ?: 23567
fun main() {
    embeddedServer(Netty, port = port, module = Application::module)
        .start(wait = true)
}


// fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureRouting()
    install(CORS){
        allowNonSimpleContentTypes
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    // DatabaseFactory.init(environment.config)
    // configureRouting()
}