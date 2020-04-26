package app.cordes.notepad.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) var id : Long,
    var title : String = "",
    var content : String = "",
    var date : Date = Date()
) : Parcelable, Comparable<Note>
{
    @Ignore
    constructor(): this(id = 0)

    override fun compareTo(other: Note): Int
    {
        return other.date.compareTo(date)
    }
}

