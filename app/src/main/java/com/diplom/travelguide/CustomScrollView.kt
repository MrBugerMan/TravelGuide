package com.diplom.travelguide

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import com.diplom.travelguide.R

class CustomScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        // Вызов метода onTouchEvent у MapView для обработки касаний
        findViewById<View>(R.id.map_country).onTouchEvent(ev)
        return false
    }
}