package io.github.obimp.packet.handle.ud.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.Bool
import io.github.obimp.data.type.LongWord
import io.github.obimp.data.type.UTF8
import io.github.obimp.data.type.Word
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_UD
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.ud.UsersDirectoryPacketHandler.Companion.OBIMP_BEX_UD_SRV_DETAILS_REQ_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class DetailsRequestPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_UD,
                    subtype = OBIMP_BEX_UD_SRV_DETAILS_REQ_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), Word(0x0000)))
                addItem(WTLD(LongWord(0x0002), UTF8("")))
                addItem(WTLD(LongWord(0x0003), UTF8("")))
                addItem(WTLD(LongWord(0x00019), Bool(true)))
            })
    }
}