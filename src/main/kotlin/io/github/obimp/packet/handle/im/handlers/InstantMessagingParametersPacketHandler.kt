package io.github.obimp.packet.handle.im.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.Bool
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_IM
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.im.InstantMessagingPacketHandler.Companion.OBIMP_BEX_IM_SRV_PARAMS_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class InstantMessagingParametersPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_IM,
                    subtype = OBIMP_BEX_IM_SRV_PARAMS_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), LongWord(255)))
                addItem(WTLD(LongWord(0x0002), LongWord(32768)))
                addItem(WTLD(LongWord(0x0003), LongWord(0)))
                addItem(WTLD(LongWord(0x0004), Bool(true)))
            })
    }
}