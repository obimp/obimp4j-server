package io.github.obimp.packet.handle.presence.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.DateTime
import io.github.obimp.data.type.LongWord
import io.github.obimp.data.type.UTF8
import io.github.obimp.data.type.Word
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_PRES
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.presence.PresencePacketHandler.Companion.OBIMP_BEX_PRES_SRV_PRES_INFO
import io.github.obimp.packet.header.OBIMPHeader
import java.time.LocalDateTime

/**
 * @author Alexander Krysin
 */
internal class RequestPresenceInfoPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_PRES,
                    subtype = OBIMP_BEX_PRES_SRV_PRES_INFO
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), UTF8("")))
                addItem(WTLD(LongWord(0x0002), DateTime(LocalDateTime.now())))
                addItem(WTLD(LongWord(0x0003), DateTime(LocalDateTime.now())))
                addItem(WTLD(LongWord(0x0004), DateTime(LocalDateTime.now())))
                addItem(WTLD(LongWord(0x0005), UTF8("")))
                addItem(WTLD(LongWord(0x0006), UTF8("")))
                addItem(WTLD(LongWord(0x0007), Word(1)))
                addItem(WTLD(LongWord(0x0008), UTF8("")))
            })
    }
}