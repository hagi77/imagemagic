package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class MagicImageView : AppCompatImageView {

    private var drawX: Float = 0f
    private var drawY: Float = 0f

    private val _paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 25f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("xxxx", "${x}, ${y}, $measuredHeight")
        event?.let { _event ->
            when (_event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("xxxx", "Action was DOWN x${event?.x} -- y${event?.y}")
                    drawX = _event.x
                    drawY = _event.y
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("xxxx", "Action was MOVE x${event?.x} -- y${event?.y}")
                    drawX = _event.x
                    drawY = _event.y
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("xxxx", "Action was UP x${event?.x} -- y${event?.y}")
                }
                MotionEvent.ACTION_CANCEL -> {
                    Log.d("xxxx", "Action was CANCEL x${event?.x} -- y${event?.y}")
                }
                MotionEvent.ACTION_OUTSIDE -> {
                    Log.d("xxxx", "Movement occurred outside bounds of current screen element")
                }
                else -> {}
            }
        }
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawPoint(drawX, drawY, _paint)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}