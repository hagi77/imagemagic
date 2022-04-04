package eu.hagisoft.imgmagic.features.modifyimage.models

import android.graphics.Path
import androidx.core.graphics.scaleMatrix

class Paths(private var viewportWidth: Int, private var viewportHeight: Int) {
    private val pathsCollection: MutableList<PathModel> = mutableListOf()

    private var currentPath: PathModel? = null

    fun newPath(x: Float, y: Float, strokeColor: Int, strokeWidth: Float) {
        currentPath = PathModel(Node(x, y), strokeColor, strokeWidth).also {
            pathsCollection.add(it)
        }
    }

    fun addToCurrentPath(x: Float, y: Float) {
        currentPath?.add(Node(x, y))
    }

    fun clear() {
        currentPath = null
        pathsCollection.clear()
    }

    fun getPaths(): List<Triple<Path, Int, Float>> =
        pathsCollection.map { Triple(it.toPath(), it.color, it.strokeWidth) }

    fun getScaledPaths(targetViewportWidth: Int, targetViewportHeight: Int): List<Triple<Path, Int, Float>> {
        val horizontalScale = targetViewportWidth.toFloat() / viewportWidth.toFloat()
        val verticalScale = targetViewportHeight.toFloat() / viewportHeight.toFloat()
        val matrix = scaleMatrix(horizontalScale, verticalScale)

        return getPaths().map {
            it.first.transform(matrix)
            Triple(it.first, it.second, it.third)
        }
    }
}
