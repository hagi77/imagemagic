package eu.hagisoft.imgmagic.features.modifyimage.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import eu.hagisoft.imgmagic.features.modifyimage.models.Node
import eu.hagisoft.imgmagic.features.modifyimage.models.PathModel

class MagicImageView : AppCompatImageView {

    private var pathModel: PathModel? = null

    private val _paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 25f
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("xxxx", "${x}, ${y}, $measuredHeight")
        event?.let { _event ->
            when (_event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("xxxx", "Action was DOWN x${event?.x} -- y${event?.y}")
                    pathModel = PathModel(Node(_event.x, _event.y), Color.GREEN, 25f)
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("xxxx", "Action was MOVE x${event?.x} -- y${event?.y}")
                    pathModel?.add(Node(_event.x, _event.y))
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    pathModel = null
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
        pathModel?.let {
            canvas?.drawPath(it.toPath(), _paint)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }
}