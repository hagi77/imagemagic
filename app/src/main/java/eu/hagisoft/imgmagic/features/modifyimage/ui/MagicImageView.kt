package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import eu.hagisoft.imgmagic.features.modifyimage.models.Node
import eu.hagisoft.imgmagic.features.modifyimage.models.PathModel

class MagicImageView : AppCompatImageView {

    private var pathModel: PathModel? = null

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { _event ->
            when (_event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    pathModel = PathModel(Node(_event.x, _event.y), strokeColor, strokeWidth)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    pathModel?.add(Node(_event.x, _event.y))
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    pathModel = null
                }
                else -> {

                }
            }
        }
        return true
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        pathModel?.let {
            updatePaint(it.color, it.strokeWidth)
            canvas?.drawPath(it.toPath(), paint)
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