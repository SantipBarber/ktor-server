package com.spbarber.plugins

import com.spbarber.model.Note
import com.spbarber.repository.NotesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/notes") {
            // CREATE
            post {
                try {
                    val note = call.receive<Note>()
                    val savedNote = NotesRepository.save(note)
                    call.respond(HttpStatusCode.Created, savedNote)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Bad JSON Data Body: ${e.message}}"
                    )
                }
            }

            // READ
            get {
                call.respond(NotesRepository.getAll())
            }

            get("{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed id"
                )
                val note = NotesRepository.getById(id.toLong()) ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "Missing or malformed id"
                )
                call.respond(note)
            }

            // UPDATE

            put {
                try {
                    val note = call.receive<Note>()
                    if (NotesRepository.update(note)) {
                        call.respond(note)
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound, "No note with this id ${note.id}"
                        )
                    }

                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        "Bad JSON Data Body: ${e.message}}"
                    )
                }
            }

            // DELETE

            delete("{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed id"
                )
                if (NotesRepository.delete(id.toLong())) {
                    call.respond(HttpStatusCode.Accepted)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound, "No note with this id $id"
                    )
                }
            }
        }
        // htmlRoute()
    }
}

//private fun Routing.htmlRoute() {
//    route("html") {
//        get() {
//            call.respondHtml(status = HttpStatusCode.OK) {
//                head {
//                    title { +"Hello Ktor" }
//                }
//                body {
//                    h1 { +"This is an h1" }
//                    p { +"This is a paragraph" }
//                    div {
//                        a(href = "html/clicked") { +"Click me" }
//                    }
//
//                }
//            }
//        }
//        get(path = "clicked") {
//            call.respondHtml {
//                head {
//                    title { +"Clicked" }
//                }
//                body {
//                    h1 { +"Button clicked" }
//                }
//            }
//        }
//    }
// }
