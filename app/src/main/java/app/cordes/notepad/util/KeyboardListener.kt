package app.cordes.notepad.util

import android.view.ViewTreeObserver
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class KeyboardListener(
    private val activity: AppCompatActivity,
    private val callback: () -> Unit
) : LifecycleObserver
{
    private val listener = object : ViewTreeObserver.OnGlobalLayoutListener
    {
        private var lastState: Boolean = activity.isKeyboardClosed()

        override fun onGlobalLayout()
        {
            val isClosed = activity.isKeyboardClosed()
            if (isClosed == lastState)
                return
            else if (isClosed)
                callback()

            lastState = isClosed
        }
    }

    init
    {
        activity.lifecycle.addObserver(this)
        activity.getRootView().viewTreeObserver.addOnGlobalLayoutListener(listener)
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_PAUSE)
    @CallSuper
    fun onLifecyclePause()
    {
        activity.getRootView().viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}