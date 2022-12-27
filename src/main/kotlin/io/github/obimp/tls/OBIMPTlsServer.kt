package io.github.obimp.tls

import org.bouncycastle.tls.*
import org.bouncycastle.tls.crypto.impl.bc.BcTlsCrypto
import java.security.SecureRandom


/**
 * @author Alexander Krysin
 */
internal class OBIMPTlsServer(
    private val certPath: String,
    private val caCertPath: String,
    private val privateKeyPath: String
) : DefaultTlsServer(BcTlsCrypto(SecureRandom())) {

    override fun getSupportedVersions(): Array<ProtocolVersion> {
        return ProtocolVersion.TLSv13.downTo(ProtocolVersion.TLSv10)
    }

    override fun getRSAEncryptionCredentials(): TlsCredentialedDecryptor {
        return OBIMPTlsUtils.loadEncryptionCredentials(context, listOf(certPath, caCertPath), privateKeyPath)
    }

    override fun getRSASignerCredentials(): TlsCredentialedSigner {
        return OBIMPTlsUtils.loadSignerCredentials(
            context,
            SignatureAlgorithm.rsa,
            listOf(certPath, caCertPath),
            privateKeyPath
        )!!
    }

    override fun getCredentials(): TlsCredentials {
        if (TlsUtils.isTLSv13(context)) {
            return rsaSignerCredentials
        }
        return super.getCredentials()
    }
}