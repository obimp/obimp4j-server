package io.github.obimp.packet.handle

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.DataStructure
import io.github.obimp.packet.Packet

/**
 * @author Alexander Krysin
 */
internal interface PacketHandler<T : DataStructure<*>> {
    fun handlePacket(connection: OBIMPClientConnection, packet: Packet<T>)
}