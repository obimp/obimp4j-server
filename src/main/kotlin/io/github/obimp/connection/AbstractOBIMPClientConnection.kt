package io.github.obimp.connection

import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import java.nio.channels.SelectionKey
import java.nio.channels.SelectionKey.OP_READ
import java.nio.channels.SelectionKey.OP_WRITE
import java.nio.channels.SocketChannel

/**
 * @author Alexander Krysin
 */
abstract class AbstractOBIMPClientConnection(channel: SocketChannel) : OBIMPClientConnection {
    protected val outputCache = mutableListOf<Packet<WTLD>>()
    protected val selectionKey: SelectionKey

    init {
        channel.configureBlocking(false)
        selectionKey = Selector.register(channel, OP_READ or OP_WRITE)
    }

    override fun sendPacket(packet: Packet<WTLD>) {
        outputCache.add(packet)
    }
}