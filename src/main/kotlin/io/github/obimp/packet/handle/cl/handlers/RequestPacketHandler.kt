package io.github.obimp.packet.handle.cl.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.BLK
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_CL
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.cl.ContactListPacketHandler.Companion.OBIMP_BEX_CL_SRV_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class RequestPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(OBIMPPacket(OBIMPHeader(type = OBIMP_BEX_CL, subtype = OBIMP_BEX_CL_SRV_REPLY)).apply {
            addItem(WTLD(LongWord(0x0001), BLK(LongWord(0).toBytes())))
        })
    }
}