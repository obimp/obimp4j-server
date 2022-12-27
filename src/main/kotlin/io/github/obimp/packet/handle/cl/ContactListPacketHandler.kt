package io.github.obimp.packet.handle.cl

import io.github.obimp.connection.OBIMPClientConnection
import io.github.obimp.data.structure.WTLD
import io.github.obimp.packet.Packet
import io.github.obimp.packet.handle.PacketHandler
import io.github.obimp.packet.handle.cl.handlers.*

/**
 * @author Alexander Krysin
 */
internal class ContactListPacketHandler : PacketHandler<WTLD> {
    private val bexSubtypeToPacketHandler = mapOf(
        Pair(OBIMP_BEX_CL_CLI_PARAMS, ContactListParametersPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_REQUEST, RequestPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_VERIFY, VerifyPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_ADD_ITEM, AddItemPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_DEL_ITEM, DeleteItemPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_UPD_ITEM, UpdateItemPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_SRV_AUTH_REQUEST, AuthorizationRequestPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_SRV_AUTH_REPLY, AuthorizationReplyPacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_SRV_AUTH_REVOKE, AuthorizationRevokePacketHandler()),
        Pair(OBIMP_BEX_CL_CLI_REQ_OFFAUTH, RequestOffauthPacketHandler())
    )

    override fun handlePacket(connection: OBIMPClientConnection, packet: Packet<WTLD>) {
        bexSubtypeToPacketHandler[packet.getSubtype()]?.handlePacket(connection, packet)
    }

    companion object {
        //BEX 0x0002, Contact list. (OBIMP_BEX_CL)
        const val OBIMP_BEX_CL_CLI_PARAMS: Short = 0x0001
        const val OBIMP_BEX_CL_SRV_PARAMS_REPLY: Short = 0x0002
        const val OBIMP_BEX_CL_CLI_REQUEST: Short = 0x0003
        const val OBIMP_BEX_CL_SRV_REPLY: Short = 0x0004
        const val OBIMP_BEX_CL_CLI_VERIFY: Short = 0x0005
        const val OBIMP_BEX_CL_SRV_VERIFY_REPLY: Short = 0x0006
        const val OBIMP_BEX_CL_CLI_ADD_ITEM: Short = 0x0007
        const val OBIMP_BEX_CL_SRV_ADD_ITEM_REPLY: Short = 0x0008
        const val OBIMP_BEX_CL_CLI_DEL_ITEM: Short = 0x0009
        const val OBIMP_BEX_CL_SRV_DEL_ITEM_REPLY: Short = 0x000A
        const val OBIMP_BEX_CL_CLI_UPD_ITEM: Short = 0x000B
        const val OBIMP_BEX_CL_SRV_UPD_ITEM_REPLY: Short = 0x000C
        const val OBIMP_BEX_CL_CLI_SRV_AUTH_REQUEST: Short = 0x000D
        const val OBIMP_BEX_CL_CLI_SRV_AUTH_REPLY: Short = 0x000E
        const val OBIMP_BEX_CL_CLI_SRV_AUTH_REVOKE: Short = 0x000F
        const val OBIMP_BEX_CL_CLI_REQ_OFFAUTH: Short = 0x0010
        const val OBIMP_BEX_CL_SRV_DONE_OFFAUTH: Short = 0x0011
        const val OBIMP_BEX_CL_CLI_DEL_OFFAUTH: Short = 0x0012
        const val OBIMP_BEX_CL_SRV_ITEM_OPER: Short = 0x0013
        const val OBIMP_BEX_CL_SRV_BEGIN_UPDATE: Short = 0x0014
        const val OBIMP_BEX_CL_SRV_END_UPDATE: Short = 0x0015
    }
}