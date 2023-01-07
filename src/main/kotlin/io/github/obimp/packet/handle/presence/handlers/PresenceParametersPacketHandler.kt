package io.github.obimp.packet.handle.presence.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_PRES
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.presence.PresencePacketHandler.Companion.OBIMP_BEX_PRES_SRV_PARAMS_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class PresenceParametersPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_PRES,
                    subtype = OBIMP_BEX_PRES_SRV_PARAMS_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), LongWord(128)))
                addItem(WTLD(LongWord(0x0002), LongWord(512)))
                addItem(WTLD(LongWord(0x0003), LongWord(128)))
                addItem(WTLD(LongWord(0x0004), LongWord(64)))
                addItem(WTLD(LongWord(0x0005), LongWord(0)))
            })
    }
}