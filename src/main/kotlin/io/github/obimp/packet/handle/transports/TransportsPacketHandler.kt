package io.github.obimp.packet.handle.transports

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.transports.handlers.ManagePacketHandler
import io.github.obimp.packet.handle.transports.handlers.SettingsPacketHandler
import io.github.obimp.packet.handle.transports.handlers.TransportsParametersPacketHandler

/**
 * @author Alexander Krysin
 */
internal class TransportsPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_TP_CLI_PARAMS, TransportsParametersPacketHandler()),
        Pair(OBIMP_BEX_TP_CLI_SETTINGS, SettingsPacketHandler()),
        Pair(OBIMP_BEX_TP_CLI_MANAGE, ManagePacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0008, Transports (OBIMP_BEX_TP)
        const val OBIMP_BEX_TP_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_TP_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_TP_SRV_ITEM_READY: Short = 0x0003
        const val OBIMP_BEX_TP_CLI_SETTINGS: Short = 0x0004
        const val OBIMP_BEX_TP_SRV_SETTINGS_REPLY: Short = 0x0005
        const val OBIMP_BEX_TP_CLI_MANAGE: Short = 0x0006
        const val OBIMP_BEX_TP_SRV_TRANSPORT_INFO: Short = 0x0007
        const val OBIMP_BEX_TP_SRV_SHOW_NOTIF: Short = 0x0008
        const val OBIMP_BEX_TP_SRV_OWN_AVATAR_HASH: Short = 0x0009
    }
}