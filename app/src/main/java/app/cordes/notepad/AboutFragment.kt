package app.cordes.notepad

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*


class AboutFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        if (activity is MainActivity)
        {
            activity?.bottom_app_bar?.visibility = View.GONE
            activity?.fab?.visibility = View.GONE
        }

        return view
    }
}
