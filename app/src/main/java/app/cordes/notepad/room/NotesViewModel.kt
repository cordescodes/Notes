package app.cordes.notepad.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NotesViewModel(application: Application) : AndroidViewModel(application)
{
    private var notesRepository : NotesRepository =
        NotesRepository(application)
    private var notes : LiveData<MutableList<Note>> = notesRepository.getAll()

    fun insert(note: Note, block : Boolean = false) : Long
    {
        if (block)
            return runBlocking { notesRepository.insert(note) }
        else
        {
            viewModelScope.launch(Dispatchers.IO) { notesRepository.insert(note) }
            return note.id
        }
    }

    fun delete(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.delete(note)
        }
    }

    fun getAll() : LiveData<MutableList<Note>>
    {
        return notes
    }
}