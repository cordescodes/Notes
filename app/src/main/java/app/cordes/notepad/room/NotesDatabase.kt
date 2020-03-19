package app.cordes.notepad.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.cordes.notepad.util.DateConverter

@Database(entities = [Note::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NotesDatabase : RoomDatabase()
{
    abstract fun noteDao() : NoteDao

    companion object
    {
        private var instance: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase?
        {
            if (instance == null)
            {
                synchronized(this)
                {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "notepad_database"
                    ).build()
                }
            }
            return instance
        }
    }
}