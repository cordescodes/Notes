package app.cordes.notepad.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import kotlin.math.round

fun Activity.getRootView(): View
{
    return findViewById<View>(android.R.id.content)
}

fun Context.dpToPx(dp: Float): Float
{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Activity.isKeyboardClosed(): Boolean
{
    val visibleBounds = Rect()
    val rootView = getRootView()
    rootView.getWindowVisibleDisplayFrame(visibleBounds)

    return (rootView.height - visibleBounds.height()) <= round(dpToPx(50F))
}