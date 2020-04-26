package app.cordes.notepad.room

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class NotesRepository(application: Application)
{
    private var noteDao : NoteDao
    private var allNotes : LiveData<MutableList<Note>>

    init
    {
        val db = NotesDatabase.getInstance(application.applicationContext)!!
        noteDao = db.noteDao()
        allNotes = noteDao.getAll()
    }

    @WorkerThread
    suspend fun insert(note: Note) : Long
    {
        return noteDao.insert(note)
    }

    @WorkerThread
    fun delete(note: Note)
    {
        noteDao.delete(note)
    }

    fun getAll() : LiveData<MutableList<Note>>
    {
        return allNotes
    }
}