package io.github.obimp.packet.handle.ft

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.ft.handlers.ControlPacketHandler
import io.github.obimp.packet.handle.ft.handlers.FileTransferParametersPacketHandler
import io.github.obimp.packet.handle.ft.handlers.SendFileReplyPacketHandler
import io.github.obimp.packet.handle.ft.handlers.SendFileRequestPacketHandler

/**
 * @author Alexander Krysin
 */
internal class FileTransferPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_FT_CLI_PARAMS, FileTransferParametersPacketHandler()),
        Pair(OBIMP_BEX_FT_CLI_SRV_SEND_FILE_REQUEST, SendFileRequestPacketHandler()),
        Pair(OBIMP_BEX_FT_CLI_SRV_SEND_FILE_REPLY, SendFileReplyPacketHandler()),
        Pair(OBIMP_BEX_FT_CLI_SRV_CONTROL, ControlPacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0007, File transfer (OBIMP_BEX_FT)
        const val OBIMP_BEX_FT_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_FT_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_FT_CLI_SRV_SEND_FILE_REQUEST: Short = 0x0003
        const val OBIMP_BEX_FT_CLI_SRV_SEND_FILE_REPLY: Short = 0x0004
        const val OBIMP_BEX_FT_CLI_SRV_CONTROL: Short = 0x0005
        const val OBIMP_BEX_FT_DIR_PROX_ERROR: Short = 0x0101
        const val OBIMP_BEX_FT_DIR_PROX_HELLO: Short = 0x0102
        const val OBIMP_BEX_FT_DIR_PROX_FILE: Short = 0x0103
        const val OBIMP_BEX_FT_DIR_PROX_FILE_REPLY: Short = 0x0104
        const val OBIMP_BEX_FT_DIR_PROX_FILE_DATA: Short = 0x0105
    }
}