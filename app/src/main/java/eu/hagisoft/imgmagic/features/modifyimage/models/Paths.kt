package eu.hagisoft.imgmagic.features.modifyimage.models

import android.graphics.Path

class Paths {
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
}
