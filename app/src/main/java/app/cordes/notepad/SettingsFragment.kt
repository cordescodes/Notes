package app.cordes.notepad

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import kotlinx.android.synthetic.main.activity_main.*

class SettingsFragment : PreferenceFragmentCompat()
{
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
    {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<ListPreference>("key_theme")?.setOnPreferenceChangeListener { _, newValue ->
            when(newValue)
            {
                "Default" -> {
                    if (Build.VERSION.SDK_INT >= 29)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                }
                "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        if (activity is MainActivity)
        {
            activity?.bottom_app_bar?.visibility = View.GONE
            activity?.fab?.visibility = View.GONE
        }

        return view
    }
}
