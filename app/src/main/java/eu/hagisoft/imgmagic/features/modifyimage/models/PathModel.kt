package eu.hagisoft.imgmagic.features.modifyimage.models

import android.graphics.Path

/**
 * Class represents a collection of nodes
 * Caution: this class is not thread safe
 */
class PathModel(val start: Node, val color: Int, val strokeWidth: Float) {
    var end: Node = start
    private var count = 1

    fun add(node: Node) {
        if (!end.isSignificantlyDifferentFrom(node)) return
        end.next = node
        end = node
        count++
    }

    fun getNodes(): List<Node> {
        val list = ArrayList<Node>(count)
        var node = start
        while (node.hasNext) {
            list.add(node)
            node = node.next!!
        }
        return list
    }

    fun toPath(): Path {
        val path = Path().apply { moveTo(start.x, start.y) }
        var node = start
        while (node.hasNext) {
            path.lineTo(node.next!!.x, node.next!!.y)
            node = node.next!!
        }
        return path
    }
}
