package eu.hagisoft.imgmagic.features.modifyimage.models

import kotlin.math.abs

data class Node(val x: Float, val y: Float) {
    var next: Node? = null

    val hasNext: Boolean
        get() = next != null

    fun isSignificantlyDifferentFrom(other: Node): Boolean =
        abs(x - other.x) > MIN_DELTA || abs(y - other.y) > MIN_DELTA

    companion object {
        private const val MIN_DELTA = 15f
    }
}