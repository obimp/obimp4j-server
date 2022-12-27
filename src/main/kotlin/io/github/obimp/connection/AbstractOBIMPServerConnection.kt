package io.github.obimp.connection

import java.net.InetSocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.SelectionKey.OP_ACCEPT
import java.nio.channels.ServerSocketChannel

/**
 * @author Alexander Krysin
 */
internal abstract class AbstractOBIMPServerConnection(private val port: Int) : OBIMPServerConnection {
    protected val channel: ServerSocketChannel = ServerSocketChannel.open()
    protected lateinit var selectionKey: SelectionKey

    override fun connect() {
        channel.configureBlocking(false)
        channel.bind(InetSocketAddress(port))
        selectionKey = Selector.register(channel, OP_ACCEPT)
        Selector.start()
    }

    override fun disconnect() {
        close()
    }

    private fun close() {
        channel.close()
        Selector.stop()
    }
}