package io.github.obimp.packet.handle.presence

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.presence.handlers.*

/**
 * @author Alexander Krysin
 */
internal class PresencePacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_PRES_CLI_PARAMS, PresenceParametersPacketHandler()),
        Pair(OBIMP_BEX_PRES_CLI_SET_PRES_INFO, SetPresenceInfoPacketHandler()),
        Pair(OBIMP_BEX_PRES_CLI_SET_STATUS, SetStatusPacketHandler()),
        Pair(OBIMP_BEX_PRES_CLI_ACTIVATE, ActivatePacketHandler()),
        Pair(OBIMP_BEX_PRES_CLI_REQ_PRES_INFO, RequestPresenceInfoPacketHandler()),
        Pair(OBIMP_BEX_PRES_CLI_REQ_OWN_MAIL_URL, RequestOwnMailUrlPacketHandler()),
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0003, Presence (OBIMP_BEX_PRES)
        const val OBIMP_BEX_PRES_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_PRES_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_PRES_CLI_SET_PRES_INFO: Short = 0x0003
        const val OBIMP_BEX_PRES_CLI_SET_STATUS: Short = 0x0004
        const val OBIMP_BEX_PRES_CLI_ACTIVATE: Short = 0x0005
        const val OBIMP_BEX_PRES_SRV_CONTACT_ONLINE: Short = 0x0006
        const val OBIMP_BEX_PRES_SRV_CONTACT_OFFLINE: Short = 0x0007
        const val OBIMP_BEX_PRES_CLI_REQ_PRES_INFO: Short = 0x0008
        const val OBIMP_BEX_PRES_SRV_PRES_INFO: Short = 0x0009
        const val OBIMP_BEX_PRES_SRV_MAIL_NOTIF: Short = 0x000A
        const val OBIMP_BEX_PRES_CLI_REQ_OWN_MAIL_URL: Short = 0x000B
        const val OBIMP_BEX_PRES_SRV_OWN_MAIL_URL: Short = 0x000C
    }
}