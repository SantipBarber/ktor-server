package com.spbarber.repository

import com.spbarber.kotlinexpert.database.AppDatabase
import com.spbarber.kotlinexpert.database.DbNote
import com.spbarber.model.Note
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import java.io.File


private const val DATABASE_DB = "database.db"

object NotesRepository {

    private val notesDb = JdbcSqliteDriver(url = "jdbc:sqlite:$DATABASE_DB").let {
        if (!File(DATABASE_DB).exists()){
            AppDatabase.Schema.create(it)
        }
        AppDatabase(it)
    }.noteQueries

    fun save(note: Note): Note {
        notesDb.insert(title = note.title, description = note.description, type = note.type.name)
        return notesDb.selectLastInsertedNote().executeAsOne().toNote()
    }

    fun getAll(): List<Note> = notesDb.select().executeAsList().map { it.toNote() }

    fun getById(id: Long): Note? = notesDb.selectById(id).executeAsOneOrNull()?.toNote()

    fun update(note: Note): Boolean {
        if (getById(note.id) == null) return false
        notesDb.update(note.title, note.description, note.type.name, note.id)
        return true
    }

    fun delete(id: Long): Boolean {
        if (getById(id) == null) return false
        notesDb.delete(id)
        return true
    }

}
private fun DbNote.toNote() = Note(id, title, description, Note.Type.valueOf(type))