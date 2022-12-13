package com.D121201044.taskmanagement.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.D121201044.taskmanagement.model.Task

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(task: Task)

    @Update
    suspend fun updateNote(task: Task)

    @Delete
    suspend fun deleteNote(task: Task)

    @Query("SELECT * FROM NOTES ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Task>>

    @Query("SELECT * FROM NOTES WHERE noteTitle LIKE :query OR noteBody LIKE:query OR noteCategory LIKE:query")
    fun searchNote(query: String?): LiveData<List<Task>>
}