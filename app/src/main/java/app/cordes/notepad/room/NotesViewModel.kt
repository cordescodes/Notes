package app.cordes.notepad.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application)
{
    private var notesRepository : NotesRepository =
        NotesRepository(application)
    private var notes : LiveData<MutableList<Note>> = notesRepository.getAll()

    fun insert(note: Note)
    {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepository.insert(note)
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