package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import eu.hagisoft.imgmagic.features.modifyimage.models.Paths

class MagicImageView : AppCompatImageView {

    private val paths = Paths()

    private var strokeWidth = DEFAULT_STROKE_WIDTH

    private var strokeColor = DEFAULT_COLOR

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setStrokeWidth(width: Float) {
        strokeWidth = width
    }

    fun setStrokeColor(color: Int) {
        strokeColor = color
    }

    fun clear() {
        paths.clear()
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { _event ->
            return when (_event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    paths.newPath(_event.x, _event.y, strokeColor, strokeWidth)
                    invalidate()
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    paths.addToCurrentPath(_event.x, _event.y)
                    invalidate()
                    true
                }
                else -> {
                    false
                }
            }
        }
        return false
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        paths.getPaths().forEach {
            updatePaint(it.second, it.third)
            canvas?.drawPath(it.first, paint)
        }
    }

    private fun updatePaint(color: Int, strokeWidth: Float) {
        paint.color = color
        paint.strokeWidth = strokeWidth
    }

    companion object {
        private const val DEFAULT_STROKE_WIDTH = 25f
        private const val DEFAULT_COLOR = Color.BLACK
    }
}