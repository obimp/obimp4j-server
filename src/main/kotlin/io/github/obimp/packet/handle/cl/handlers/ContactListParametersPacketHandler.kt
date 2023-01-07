package io.github.obimp.packet.handle.cl.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.Bool
import io.github.obimp.data.type.LongWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_CL
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.cl.ContactListPacketHandler.Companion.OBIMP_BEX_CL_SRV_PARAMS_REPLY
import io.github.obimp.packet.header.OBIMPHeader

/**
 * @author Alexander Krysin
 */
internal class ContactListParametersPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_CL,
                    subtype = OBIMP_BEX_CL_SRV_PARAMS_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), LongWord(1000)))
                addItem(WTLD(LongWord(0x0002), LongWord(255)))
                addItem(WTLD(LongWord(0x0003), LongWord(8000)))
                addItem(WTLD(LongWord(0x0004), LongWord(255)))
                addItem(WTLD(LongWord(0x0005), LongWord(255)))
                addItem(WTLD(LongWord(0x0006), LongWord(2048)))
                addItem(WTLD(LongWord(0x0007), LongWord(0)))
                addItem(WTLD(LongWord(0x0008), LongWord(0)))
                addItem(WTLD(LongWord(0x0009), LongWord(0)))
                addItem(WTLD(LongWord(0x000A), Bool(false)))
                addItem(WTLD(LongWord(0x000B), LongWord(1000)))
                addItem(WTLD(LongWord(0x000C), LongWord(255)))
                addItem(WTLD(LongWord(0x000D), LongWord(1024)))
            })
    }
}