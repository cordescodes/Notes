package app.cordes.notepad

import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager

class App : Application()
{
    override fun onCreate()
    {
        super.onCreate()

        when(PreferenceManager.getDefaultSharedPreferences(this)
            .getString("key_theme", "Default"))
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
    }
}