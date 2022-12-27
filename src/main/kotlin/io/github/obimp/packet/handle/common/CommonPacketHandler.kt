package io.github.obimp.packet.handle.common

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.common.handlers.*

/**
 * @author Alexander Krysin
 */
class CommonPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_COM_CLI_HELLO, HelloPacketHandler()),
        Pair(OBIMP_BEX_COM_CLI_LOGIN, LoginPacketHandler()),
        Pair(OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PING, KeepalivePingPacketHandler()),
        Pair(OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PONG, KeepalivePongPacketHandler()),
        Pair(OBIMP_BEX_COM_CLI_REGISTER, RegisterPacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0001, Common. (OBIMP_BEX_COM)
        const val OBIMP_BEX_COM_CLI_HELLO: Short = 0x0001
        const val OBIMP_BEX_COM_SRV_HELLO: Short = 0x0002
        const val OBIMP_BEX_COM_CLI_LOGIN: Short = 0x0003
        const val OBIMP_BEX_COM_SRV_LOGIN_REPLY: Short = 0x0004
        const val OBIMP_BEX_COM_SRV_BYE: Short = 0x0005
        const val OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PING: Short = 0x0006
        const val OBIMP_BEX_COM_CLI_SRV_KEEPALIVE_PONG: Short = 0x0007
        const val OBIMP_BEX_COM_CLI_REGISTER: Short = 0x0008
        const val OBIMP_BEX_COM_SRV_REGISTER_REPLY: Short = 0x0009
    }
}