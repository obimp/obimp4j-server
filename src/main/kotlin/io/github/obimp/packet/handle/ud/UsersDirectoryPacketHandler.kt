package io.github.obimp.packet.handle.ud

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.ud.handlers.*

/**
 * @author Alexander Krysin
 */
internal class UsersDirectoryPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_UD_CLI_PARAMS, UsersDirectoryParametersPacketHandler()),
        Pair(OBIMP_BEX_UD_CLI_DETAILS_REQ, DetailsRequestPacketHandler()),
        Pair(OBIMP_BEX_UD_CLI_DETAILS_UPD, DetailsUpdatePacketHandler()),
        Pair(OBIMP_BEX_UD_CLI_SEARCH, SearchPacketHandler()),
        Pair(OBIMP_BEX_UD_CLI_SECURE_UPD, SecureUpdatePacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0005, Users directory (OBIMP_BEX_UD)
        const val OBIMP_BEX_UD_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_UD_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_UD_CLI_DETAILS_REQ: Short = 0x0003
        const val OBIMP_BEX_UD_SRV_DETAILS_REQ_REPLY: Short = 0x0004
        const val OBIMP_BEX_UD_CLI_DETAILS_UPD: Short = 0x0005
        const val OBIMP_BEX_UD_SRV_DETAILS_UPD_REPLY: Short = 0x0006
        const val OBIMP_BEX_UD_CLI_SEARCH: Short = 0x0007
        const val OBIMP_BEX_UD_SRV_SEARCH_REPLY: Short = 0x0008
        const val OBIMP_BEX_UD_CLI_SECURE_UPD: Short = 0x0009
        const val OBIMP_BEX_UD_SRV_SECURE_UPD_REPLY: Short = 0x000A
    }
}