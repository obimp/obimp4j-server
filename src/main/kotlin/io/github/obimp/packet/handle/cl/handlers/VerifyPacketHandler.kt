package io.github.obimp.packet.handle.cl.handlers

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.data.type.LongWord
import io.github.obimp.data.type.OctaWord
import io.github.obimp.packet.OBIMPPacket
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.OBIMPPacketHandler.Companion.OBIMP_BEX_CL
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.cl.ContactListPacketHandler.Companion.OBIMP_BEX_CL_SRV_VERIFY_REPLY
import io.github.obimp.packet.header.OBIMPHeader
import java.nio.ByteBuffer

/**
 * @author Alexander Krysin
 */
internal class VerifyPacketHandler : PacketHandler<WTLD> {
    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        connection.sendPacket(
            OBIMPPacket(
                OBIMPHeader(
                    type = OBIMP_BEX_CL,
                    subtype = OBIMP_BEX_CL_SRV_VERIFY_REPLY
                )
            ).apply {
                addItem(WTLD(LongWord(0x0001), OctaWord(ByteBuffer.allocate(16))))
            })
    }
}