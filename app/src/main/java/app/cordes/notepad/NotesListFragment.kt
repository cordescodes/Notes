package app.cordes.notepad

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.cordes.notepad.room.Note
import app.cordes.notepad.room.NotesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_list.view.*

class NotesListFragment : Fragment()
{
    private var listener: OnInteractionListener? = null

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if (context is OnInteractionListener)
            listener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        if (activity is MainActivity)
        {
            activity?.bottom_app_bar?.visibility = View.VISIBLE
            activity?.fab?.visibility = View.VISIBLE
        }

        if (view.list is RecyclerView)
        {
            view.list.layoutManager = LinearLayoutManager(context)
            view.list.adapter = NotesAdapter(listener!!)

            val notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
            notesViewModel.notes.observe(viewLifecycleOwner,
                Observer<MutableList<Note>> { notes -> (view.list.adapter as NotesAdapter).set(notes)})
        }

        return view
    }

    override fun onDetach()
    {
        super.onDetach()
        listener = null
    }

    interface OnInteractionListener
    {
        fun onInteraction(noteItem: NoteItem)
    }

    fun filter(query: String)
    {
        (view?.list?.adapter as NotesAdapter).filter(query)
    }
}
