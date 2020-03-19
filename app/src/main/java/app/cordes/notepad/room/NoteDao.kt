package app.cordes.notepad.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Query("SELECT * FROM note_table")
    fun getAll() : LiveData<MutableList<Note>>
}