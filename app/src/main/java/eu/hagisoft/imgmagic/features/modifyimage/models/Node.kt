package eu.hagisoft.imgmagic.features.modifyimage.models

data class Node(val x: Float, val y: Float) {
    var next: Node? = null

    val hasNext: Boolean
        get() = next != null
}