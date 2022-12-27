package io.github.obimp.connection

import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet

/**
 * @author Alexander Krysin
 */
sealed interface OBIMPClientConnection {
    fun sendPacket(packet: Packet<WTLD>)
}