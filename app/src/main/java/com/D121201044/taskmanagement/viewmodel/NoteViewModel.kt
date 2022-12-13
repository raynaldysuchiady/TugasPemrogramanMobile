package com.D121201044.taskmanagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.D121201044.taskmanagement.model.Task
import com.D121201044.taskmanagement.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
    app: Application,
    private val noteRepository: NoteRepository
) : AndroidViewModel(app) {


    fun addNote(task: Task) =
        viewModelScope.launch {
            noteRepository.insertNote(task)
        }

    fun deleteNote(task: Task) =
        viewModelScope.launch {
            noteRepository.deleteNote(task)
        }

    fun updateNote(task: Task) =
        viewModelScope.launch {
            noteRepository.updateNote(task)
        }

    fun getAllNote() = noteRepository.getAllNotes()

    fun searchNote(query: String?) =
        noteRepository.searchNote(query)



}