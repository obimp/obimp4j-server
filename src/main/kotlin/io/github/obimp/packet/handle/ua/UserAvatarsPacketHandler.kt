package io.github.obimp.packet.handle.ua

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.ua.handlers.AvatarRequestPacketHandler
import io.github.obimp.packet.handle.ua.handlers.AvatarSetPacketHandler
import io.github.obimp.packet.handle.ua.handlers.UserAvatarsParametersPacketHandler

/**
 * @author Alexander Krysin
 */
internal class UserAvatarsPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_UA_CLI_PARAMS, UserAvatarsParametersPacketHandler()),
        Pair(OBIMP_BEX_UA_CLI_AVATAR_REQ, AvatarRequestPacketHandler()),
        Pair(OBIMP_BEX_UA_CLI_AVATAR_SET, AvatarSetPacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0006, User avatars (OBIMP_BEX_UA)
        const val OBIMP_BEX_UA_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_UA_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_UA_CLI_AVATAR_REQ: Short = 0x0003
        const val OBIMP_BEX_UA_SRV_AVATAR_REPLY: Short = 0x0004
        const val OBIMP_BEX_UA_CLI_AVATAR_SET: Short = 0x0005
        const val OBIMP_BEX_UA_SRV_AVATAR_SET_REPLY: Short = 0x0006
    }
}