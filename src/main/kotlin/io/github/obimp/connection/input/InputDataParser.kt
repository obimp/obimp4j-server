package io.github.obimp.connection.input

import io.github.obimp.connection.OBIMPClientConnection
import java.nio.ByteBuffer

/**
 * @author Alexander Krysin
 */
internal interface InputDataParser {
    fun parseInputData(connection: OBIMPClientConnection, buffer: ByteBuffer)
}
