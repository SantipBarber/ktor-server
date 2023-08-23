package com.spbarber

import com.spbarber.plugins.configureRouting
import com.spbarber.plugins.configureSerialization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*

//fun main() {
//    embeddedServer(Netty, port = (System.getenv("PORT")?:"5000").toInt(), module = Application::module)
//        .start(wait = true)
//}


val port = System.getenv("PORT")?.toInt() ?: 23567
fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureRouting()
    install(CORS){
        anyHost()
        allowNonSimpleContentTypes
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }
    // DatabaseFactory.init(environment.config)
    // configureRouting()
}