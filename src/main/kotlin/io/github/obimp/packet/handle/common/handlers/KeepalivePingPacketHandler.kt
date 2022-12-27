package io.github.obimp.packet.handle.common.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_COM
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.common.CommonPacketHandler.Companion.OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PONG

/**
 * @author Alexander Krysin
 */
internal class KeepalivePingPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(OBIMPPacket(OBIMP_BEX_COM, OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PONG))
    }
}