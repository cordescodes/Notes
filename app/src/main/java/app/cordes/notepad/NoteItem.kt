package app.cordes.notepad

import android.content.Context
import androidx.preference.PreferenceManager
import app.cordes.notepad.room.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteItem(private val note : Note, context: Context)
{
    val showLastModified = PreferenceManager.getDefaultSharedPreferences(context)
        .getBoolean("key_show_last_modified", true)

    private val locale : Locale = Locale.getDefault()
    private val dateFormat = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("key_date_format", "M/d/yy")
    private val timeFormat = PreferenceManager.getDefaultSharedPreferences(context)
        .getString("key_time_format", "h:mm a")

    fun getNote() : Note { return note }
    fun getTitle() : String { return note.title }
    fun getContent() : String { return note.content }

    fun getDisplayDate() : String
    {
        return SimpleDateFormat(dateFormat, locale).format(note.date)
    }

    fun getDisplayTime() : String
    {
        return SimpleDateFormat(timeFormat, locale).format(note.date)
    }
}