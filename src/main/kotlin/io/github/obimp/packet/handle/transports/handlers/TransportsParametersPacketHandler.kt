package io.github.obimp.packet.handle.transports.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.BLK
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_TP
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.transports.TransportsPacketHandler.Companion.OBIMP_BEX_TP_SRV_PARAMS_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class TransportsParametersPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_TP,
                    subtype = OBIMP_BEX_TP_SRV_PARAMS_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), LongWord(5)))
                addItem(WTLD(LongWord(0x0002), BLK(LongWord(0).toBytes())))
            })
    }
}