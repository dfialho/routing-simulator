package bgp

/**
 * Created on 21-07-2017
 *
 * @author David Fialho
 */
sealed class BaseBGPProtocol {

    /**
     * Flag that indicates if a new route was selected as a result of processing a new incoming message. This flag is
     * always set to false when a new message arrives and should only be set to true if a new route is selected when
     * the message is being processed.
     */
    private var wasNewRouteSelected: Boolean = false

    /**
     * Indicates if the selected route was updated in the last call to process.
     */
    fun wasSelectedRouteUpdated(): Boolean = wasNewRouteSelected

    /**
     * Processes a BGP message received by a node.
     * May updated the routing table and the selected route/neighbor.
     *
     * @param message the message to be processed
     */
    fun process(message: BGPMessage) {

    }

    /**
     * Implements the process of importing a route.
     * Returns the result of extending the given route with the given extender.
     *
     * @param route    the route received by the node (route obtained directly from the message)
     * @param extender the extender used to import the route (extender included in the message)
     */
    fun import(route: BGPRoute, extender: BGPExtender): BGPRoute {
        return extender.extend(route)
    }

    /**
     * Implements the process of learning a route.
     *
     * @param node   the node processing the route
     * @param sender the out-neighbor that sent the route
     * @param route  the route imported by the node (route obtained after applying the extender)
     * @return the imported route if the route's AS-PATH does not include the node learning the route or it returns
     * an invalid if the route's AS-PATH includes the learning node. Note that it may also return an invalid route if
     * the imported route is invalid.
     */
    fun learn(node: BGPNode, sender: BGPNode, route: BGPRoute): BGPRoute {

        if (node in route.asPath) {
            // Notify the implementations that a loop was detected
            onLoopDetected(sender, route)

            return BGPRoute.invalid()
        } else {
            return route
        }
    }

    /**
     * Implements the process of exporting a route.
     *
     * @param node  the node processing the route
     * @param route the route to be exported (route selected by the node)
     */
    fun export(node: BGPNode, route: BGPRoute) {

    }

    /**
     * Called by the protocol when it detects a routing loop.
     */
    protected abstract fun onLoopDetected(sender: BGPNode, route: BGPRoute)

}

//region Subclasses

/**
 * BGP Protocol: when a loop is detected it does nothing
 */
class BGPProtocol : BaseBGPProtocol() { override fun onLoopDetected(sender: BGPNode, route: BGPRoute) = Unit }

/**
 * SS-BGP Protocol: when a loop is detected it tries to detect if the loop is recurrent using the WEAK detection
 * condition. If it determines the loop is recurrent, it disables the neighbor that exported the route.
 */
class SSBGPProtocol : BaseBGPProtocol() {

    override fun onLoopDetected(sender: BGPNode, route: BGPRoute) {
        TODO("not implemented")
    }
}

/**
 * SS-BGP Protocol: when a loop is detected it tries to detect if the loop is recurrent using the STRONG detection
 * condition. If it determines the loop is recurrent, it disables the neighbor that exported the route.
 */
class ISSBGPProtocol : BaseBGPProtocol() {
    override fun onLoopDetected(sender: BGPNode, route: BGPRoute) {
        TODO("not implemented")
    }
}

//endregion