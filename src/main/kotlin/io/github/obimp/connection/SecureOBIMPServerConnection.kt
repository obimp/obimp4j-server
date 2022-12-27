package io.github.obimp.connection

import io.github.obimp.tls.OBIMPTlsServer
import org.bouncycastle.tls.TlsServerProtocol

/**
 * @author Alexander Krysin
 */
internal class SecureOBIMPServerConnection(
    port: Int,
    certPath: String,
    caCertPath: String,
    privateKeyPath: String
) : AbstractOBIMPServerConnection(port) {
    private val tlsServer = OBIMPTlsServer(certPath, caCertPath, privateKeyPath)

    override fun connect() {
        super.connect()
        selectionKey.attach(Runnable {
            if (selectionKey.isAcceptable) {
                channel.accept()?.let {
                    SecureOBIMPClientConnection(it, tlsServer).start()
                }
            }
        })
    }
}