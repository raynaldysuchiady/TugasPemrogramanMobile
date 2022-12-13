package com.D121201044.taskmanagement.repository

import com.D121201044.taskmanagement.db.NoteDatabase
import com.D121201044.taskmanagement.model.Task

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(task: Task) = db.getNoteDao().insertNote(task)
    suspend fun deleteNote(task: Task) = db.getNoteDao().deleteNote(task)
    suspend fun updateNote(task: Task) = db.getNoteDao().updateNote(task)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}