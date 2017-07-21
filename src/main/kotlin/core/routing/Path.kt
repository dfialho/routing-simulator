package core.routing

/**
 * Created on 21-07-2017
 *
 * @author David Fialho
 *
 * A Path is a sequence of nodes that form a path in a network. Nodes appended to a path are kept in order.
 * Nodes can be repeated in a path. That is, a path may include two or more nodes with the same ID.
 *
 * Path instances are immutable!
 *
 * @property size expresses the number of nodes in the path
 */
class Path<N: Node>(private val nodes: List<N>) {

    val size: Int = nodes.size

    /**
     * Returns a new path instance containing the nodes in the same order as this path and with the given node
     * appended to it.
     */
    fun append(node: N): Path<N> {
        val nodesCopy = ArrayList(nodes)
        nodesCopy.add(node)

        return Path(nodesCopy)
    }

}

//region Factory methods

/**
 * Returns a path with no nodes.
 */
fun <N: Node> emptyPath(): Path<N> {
    return Path(emptyList())
}

/**
 * Returns a path containing the given nodes in the same order as they are given.
 */
fun <N: Node> pathOf(vararg nodes: N): Path<N> {
    return Path(listOf(*nodes))
}

/**
 * Returns an empty path.
 */
inline fun <N: Node> pathOf(): Path<N> {
    return emptyPath()
}

//endregion