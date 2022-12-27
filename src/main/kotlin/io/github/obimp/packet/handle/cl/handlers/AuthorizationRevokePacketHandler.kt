package io.github.obimp.packet.handle.cl.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.structure.readDataType
import io.github.obimp.data.type.UTF8
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler

/**
 * @author Alexander Krysin
 */
internal class AuthorizationRevokePacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        val accountName = packet.nextItem().readDataType<UTF8>().value
        val reason = packet.nextItem().readDataType<UTF8>().value
    }
}