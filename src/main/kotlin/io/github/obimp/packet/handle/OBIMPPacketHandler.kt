package io.github.obimp.packet.handle

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.cl.ContactListPacketHandler
import io.github.obimp.packet.handle.common.CommonPacketHandler
import io.github.obimp.packet.handle.ft.FileTransferPacketHandler
import io.github.obimp.packet.handle.im.InstantMessagingPacketHandler
import io.github.obimp.packet.handle.presence.PresencePacketHandler
import io.github.obimp.packet.handle.transports.TransportsPacketHandler
import io.github.obimp.packet.handle.ua.UserAvatarsPacketHandler
import io.github.obimp.packet.handle.ud.UsersDirectoryPacketHandler

/**
 * @author Alexander Krysin
 */
internal class OBIMPPacketHandler : PacketHandler<WTLD> {
    private val bexTypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_COM, CommonPacketHandler()),
        Pair(OBIMP_BEX_CL, ContactListPacketHandler()),
        Pair(OBIMP_BEX_PRES, PresencePacketHandler()),
        Pair(OBIMP_BEX_IM, InstantMessagingPacketHandler()),
        Pair(OBIMP_BEX_UD, UsersDirectoryPacketHandler()),
        Pair(OBIMP_BEX_UA, UserAvatarsPacketHandler()),
        Pair(OBIMP_BEX_FT, FileTransferPacketHandler()),
        Pair(OBIMP_BEX_TP, TransportsPacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexTypeToPacketHandler[packet.getType()]?.handlePacket(connection, packet)
    }

    companion object {
        //General BEX types
        const val OBIMP_BEX_COM: Short = 0x0001 // Common
        const val OBIMP_BEX_CL: Short = 0x0002 // Contact list
        const val OBIMP_BEX_PRES: Short = 0x0003 // Presence
        const val OBIMP_BEX_IM: Short = 0x0004 // Instant messaging
        const val OBIMP_BEX_UD: Short = 0x0005 // Users directory
        const val OBIMP_BEX_UA: Short = 0x0006 // User avatars
        const val OBIMP_BEX_FT: Short = 0x0007 // File transfer
        const val OBIMP_BEX_TP: Short = 0x0008 // Transports
    }
}