package app.cordes.notepad

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import app.cordes.notepad.room.Note
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NotesListFragment.OnInteractionListener
{
    enum class FragmentEnum(val index: Int, val tag: String)
    {
        NOTES_LIST(0, "notes_list_fragment"),
        SETTINGS(1, "settings_fragment"),
        ABOUT(2, "about_fragment")
    }

    private val fragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)

        fab.setOnClickListener {
            startActivity(
                Intent(this, NoteActivity::class.java)
                    .putExtra(NoteActivity.EXTRA_NOTE, Note()))
        }

        fragments.add(NotesListFragment())
        fragments.add(SettingsFragment())
        fragments.add(AboutFragment())
        switchFragment(FragmentEnum.NOTES_LIST, false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query : String) : Boolean { return false }
            override fun onQueryTextChange(newText : String) : Boolean
            {
                (fragments[0] as NotesListFragment).filter(newText)
                return false
            }
        })

        searchView.setOnSearchClickListener {
            fab.hide()
            bottom_app_bar.fabCradleMargin = 0F
            bottom_app_bar.fabCradleRoundedCornerRadius = 0F
        }

        searchView.setOnCloseListener {
            fab.show()
            bottom_app_bar.fabCradleMargin = 12F
            bottom_app_bar.fabCradleRoundedCornerRadius = 12F
            false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when (item.itemId)
        {
            R.id.action_settings -> {
                switchFragment(FragmentEnum.SETTINGS)
                true
            }
            R.id.action_about -> {
                switchFragment(FragmentEnum.ABOUT)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun switchFragment(f: FragmentEnum, addToBackStack: Boolean = true)
    {
        val manager = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_frame, fragments[f.index], f.tag)
        if (addToBackStack)
            manager.addToBackStack(null)
        manager.commit()
    }

    override fun onInteraction(noteItem: NoteItem)
    {
        startActivity(
            Intent(this, NoteActivity::class.java)
                .putExtra(NoteActivity.EXTRA_NOTE, noteItem.getNote()))
    }
}
