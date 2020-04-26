package app.cordes.notepad.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note) : Long

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAll()

    @Query("SELECT * FROM note_table")
    fun getAll() : LiveData<MutableList<Note>>
}