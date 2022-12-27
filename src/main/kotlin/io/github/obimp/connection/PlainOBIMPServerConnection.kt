package io.github.obimp.connection

/**
 * @author Alexander Krysin
 */
internal class PlainOBIMPServerConnection(port: Int) : AbstractOBIMPServerConnection(port) {
    override fun connect() {
        super.connect()
        selectionKey.attach(Runnable {
            if (selectionKey.isAcceptable) {
                channel.accept()?.let { PlainOBIMPClientConnection(it) }
            }
        })
    }
}