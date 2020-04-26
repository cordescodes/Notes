package app.cordes.notepad

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import app.cordes.notepad.databinding.ActivityNoteBinding
import app.cordes.notepad.room.Note
import app.cordes.notepad.room.NotesViewModel
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*


class NoteActivity : AppCompatActivity()
{
    private lateinit var note: Note
    private var noteChanged = false
    private var autoSaveEnabled = true
    private var confirmDelete = true
    private var showToast = true

    private val textWatcher = object : TextWatcher
    {
        override fun afterTextChanged(a : Editable) { noteChanged = true }
        override fun beforeTextChanged(a : CharSequence, b : Int, c : Int, d : Int) {}
        override fun onTextChanged(a : CharSequence, b : Int, c : Int, d : Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        if (Intent.ACTION_SEND == intent.action && "text/plain" == intent.type)
        {
            note = Note()
            note.content = intent.getStringExtra(Intent.EXTRA_TEXT)
        }
        else
            note = intent.getParcelableExtra(EXTRA_NOTE)

        val binding: ActivityNoteBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_note)
        binding.setVariable(BR.note, note)
        binding.setVariable(BR.handler, this)
        binding.executePendingBindings()

        autoSaveEnabled = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("key_auto_save", true)
        confirmDelete = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("key_confirm_delete", true)
        showToast = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("key_show_toast", true)

        fab_save.visibility = if (autoSaveEnabled) View.GONE else View.VISIBLE

        note_title.addTextChangedListener(textWatcher)
        note_content.addTextChangedListener(textWatcher)

        setSupportActionBar(toolbar)

        if (autoSaveEnabled && note.id == 0L)
           note.id = ViewModelProvider(this).get(NotesViewModel::class.java).insert(note, true)

        if (TextUtils.isEmpty(note.content))
            note_content.requestFocus()
    }

    override fun onPause()
    {
        if (noteChanged && autoSaveEnabled)
            save(note, false)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, note.title + "\n" + note.content)
                startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
                true
            }
            R.id.action_duplicate -> {
                val copy = Note()
                copy.title = note.title
                copy.content = note.content
                save(copy, false, true)
                if (showToast)
                    Toast.makeText(this, R.string.duplicated, Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_delete -> {
                if (confirmDelete)
                {
                    val color = getColor(R.color.colorContrast)
                    val dialog = AlertDialog.Builder(this)
                        .setTitle(R.string.confirm_delete_prompt)
                        .setMessage(R.string.cannot_be_undone)
                        .setPositiveButton(R.string.yes) { _, _ -> delete() }
                        .setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
                        .show()
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.setTextColor(color)
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE)?.setTextColor(color)
                }
                else
                    delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun save(n : Note, finish : Boolean, duplicateOperation : Boolean = false)
    {
        n.date = Calendar.getInstance().time
        ViewModelProvider(this).get(NotesViewModel::class.java).insert(n)

        if (showToast && !duplicateOperation)
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show()

        if (finish)
            finish()
        else
            noteChanged = false
    }

    private fun delete()
    {
        ViewModelProvider(this).get(NotesViewModel::class.java).delete(note)
        if (showToast)
            Toast.makeText(this, R.string.deleted, Toast.LENGTH_SHORT).show()
        finish()
    }

    companion object
    {
        const val EXTRA_NOTE = "app.cordes.notes.NOTE"
    }
}
