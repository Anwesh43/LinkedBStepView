package ui.anwesome.com.bstepview

/**
 * Created by anweshmishra on 19/10/18.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.*
import android.content.Context

val nodes : Int = 5

class BStepView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}