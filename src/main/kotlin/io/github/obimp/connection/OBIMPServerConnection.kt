package io.github.obimp.connection

/**
 * @author Alexander Krysin
 */
internal sealed interface OBIMPServerConnection {
    fun connect()
    fun disconnect()
}