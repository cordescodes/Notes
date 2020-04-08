package app.cordes.notepad

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.cordes.notepad.databinding.NoteItemBinding
import app.cordes.notepad.room.Note
import java.util.Collections.sort

class NotesAdapter(private val listener: NotesListFragment.OnInteractionListener)
    : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>()
{
    private var visibleNotes : MutableList<Note> = mutableListOf()
    private var allNotes : MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : NoteViewHolder
    {
        return NoteViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int)
    {
        holder.bind(visibleNotes[position])
    }

    override fun getItemCount(): Int
    {
        return visibleNotes.size
    }

    fun set(notes : MutableList<Note>)
    {
        sort(notes)
        allNotes = notes
        visibleNotes = notes
        notifyDataSetChanged()
    }

    fun filter(query : String)
    {
        if (TextUtils.isEmpty(query))
        {
            // search cleared, show all notes again
            visibleNotes = allNotes
            notifyDataSetChanged()
            return
        }

        val searchResults = mutableListOf<Note>()
        if (allNotes.isNotEmpty())
        {
            for (note in allNotes)
            {
                if ((!TextUtils.isEmpty(note.title) && note.title.contains(query, true))
                    || (!TextUtils.isEmpty(note.content) && note.content.contains(query, true)))
                {
                    searchResults.add(note)
                }
            }

            // display results, even if they're empty
            visibleNotes = searchResults
            notifyDataSetChanged()
        }
    }

    inner class NoteViewHolder(
        private val binding: NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(note: Note)
        {
            binding.setVariable(BR.item, NoteItem(note, binding.root.context))
            binding.setVariable(BR.handler, listener)
            binding.executePendingBindings()
        }
    }
}