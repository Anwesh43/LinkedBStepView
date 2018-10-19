package ui.anwesome.com.bstepview

/**
 * Created by anweshmishra on 19/10/18.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.*
import android.content.Context

val nodes : Int = 5

fun Canvas.drawBSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val r : Float = gap/6
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = Math.min(w, h) / 60
    paint.color = Color.parseColor("#673AB7")
    paint.strokeCap = Paint.Cap.ROUND
    save()
    translate(gap + i * gap, h/2)
    for (j in 0..1) {
        val sc : Float = Math.min(0.5f, Math.max(0f, scale - 0.5f * j)) * 2
        save()
        scale(1f, 1f - 2 * j)
        drawLine(0f, -r - r * sc, 0f, -r + r * sc, paint)
        drawArc(RectF(-2 * r, 0f, 0f, 2 * r), -90f * sc, 180f * sc, false, paint)
        restore()
    }
    restore()
}

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

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += 0.05f * dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class BSNode(var i : Int, val state : State = State()) {

        private var next : BSNode? = null
        private var prev : BSNode? = null

        init {
            addNeighbor()
        }

        fun addNeighbor() {
            if (i < nodes - 1) {
                this.next = BSNode(i + 1)
                next?.prev = this
            }
        }

        fun draw(canvas : Canvas, paint : Paint) {
            canvas.drawBSNode(i, state.scale, paint)
            prev?.draw(canvas, paint)
        }

        fun update(cb : (Int, Float) -> Unit) {
            state.update {
                cb(i, it)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            state.startUpdating(cb)
        }

        fun getNext(dir : Int, cb : () -> Unit) : BSNode {
            var curr : BSNode? = prev
            if (dir == 1) {
                curr = next
            }
            if (curr != null) {
                return curr
            }
            cb()
            return this
        }
    }
}