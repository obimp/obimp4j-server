package io.github.obimp.tls

import org.bouncycastle.asn1.edec.EdECObjectIdentifiers
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.asn1.pkcs.RSAPrivateKey
import org.bouncycastle.asn1.sec.ECPrivateKey
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers
import org.bouncycastle.crypto.params.AsymmetricKeyParameter
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters
import org.bouncycastle.crypto.util.PrivateKeyFactory
import org.bouncycastle.tls.*
import org.bouncycastle.tls.crypto.TlsCertificate
import org.bouncycastle.tls.crypto.TlsCrypto
import org.bouncycastle.tls.crypto.TlsCryptoParameters
import org.bouncycastle.tls.crypto.impl.bc.BcDefaultTlsCredentialedDecryptor
import org.bouncycastle.tls.crypto.impl.bc.BcDefaultTlsCredentialedSigner
import org.bouncycastle.tls.crypto.impl.bc.BcTlsCrypto
import org.bouncycastle.tls.crypto.impl.jcajce.JcaDefaultTlsCredentialedSigner
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto
import org.bouncycastle.tls.crypto.impl.jcajce.JceDefaultTlsCredentialedDecryptor
import org.bouncycastle.util.io.pem.PemObject
import org.bouncycastle.util.io.pem.PemReader
import java.io.FileReader
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.RSAPrivateCrtKeySpec

/**
 * @author Alexander Krysin
 */
internal object OBIMPTlsUtils {
    fun loadEncryptionCredentials(
        context: TlsContext,
        certificateResources: List<String>,
        privateKeyResource: String
    ): TlsCredentialedDecryptor {
        val crypto = context.crypto
        val certificate = loadCertificateChain(context.serverVersion, context.crypto, certificateResources)

        return if (crypto is BcTlsCrypto) {
            val privateKey = loadBcPrivateKeyResource(privateKeyResource)
            BcDefaultTlsCredentialedDecryptor(crypto, certificate, privateKey)
        } else {
            val jcaCrypto = crypto as JcaTlsCrypto
            val privateKey = loadJcaPrivateKeyResource(jcaCrypto, privateKeyResource)
            JceDefaultTlsCredentialedDecryptor(jcaCrypto, certificate, privateKey)
        }
    }

    fun loadSignerCredentials(
        context: TlsContext,
        signatureAlgorithm: Short,
        certificateResources: List<String>,
        privateKeyResource: String
    ): TlsCredentialedSigner? {
        val signatureAndHashAlgorithm = selectSignatureAndHashAlgorithm(context, signatureAlgorithm) ?: return null
        val crypto = context.crypto
        val cryptoParams = TlsCryptoParameters(context)
        val certificate = loadCertificateChain(cryptoParams.serverVersion, crypto, certificateResources)

        return if (crypto is BcTlsCrypto) {
            val privateKey = loadBcPrivateKeyResource(privateKeyResource)
            BcDefaultTlsCredentialedSigner(cryptoParams, crypto, privateKey, certificate, signatureAndHashAlgorithm)
        } else {
            val jcaCrypto = crypto as JcaTlsCrypto
            val privateKey = loadJcaPrivateKeyResource(jcaCrypto, privateKeyResource)
            JcaDefaultTlsCredentialedSigner(cryptoParams, jcaCrypto, privateKey, certificate, signatureAndHashAlgorithm)
        }
    }

    private fun selectSignatureAndHashAlgorithm(
        context: TlsContext,
        signatureAlgorithm: Short
    ): SignatureAndHashAlgorithm? {
        val clientSigAlgs = context.securityParametersHandshake.clientSigAlgs
        val supportedSignatureAlgorithms = clientSigAlgs ?: TlsUtils.getDefaultSignatureAlgorithms(signatureAlgorithm)

        for (supportedSignatureAlgorithm in supportedSignatureAlgorithms) {
            if ((supportedSignatureAlgorithm as SignatureAndHashAlgorithm).signature == signatureAlgorithm) {
                return supportedSignatureAlgorithm
            }
        }

        return null
    }

    private fun loadCertificateChain(
        protocolVersion: ProtocolVersion,
        crypto: TlsCrypto,
        resources: List<String>
    ): Certificate {
        return if (TlsUtils.isTLSv13(protocolVersion)) {
            val certificateEntryList = mutableListOf<CertificateEntry>()
            for (resource in resources) {
                certificateEntryList.add(CertificateEntry(loadCertificateResource(crypto, resource), null))
            }
            Certificate(TlsUtils.EMPTY_BYTES, certificateEntryList.toTypedArray())
        } else {
            val chain = mutableListOf<TlsCertificate>()
            for (resource in resources) {
                chain.add(loadCertificateResource(crypto, resource))
            }
            Certificate(chain.toTypedArray())
        }
    }

    private fun loadCertificateResource(crypto: TlsCrypto, resource: String): TlsCertificate {
        val pemObject = loadPemResource(resource)

        if (pemObject.type.endsWith("CERTIFICATE")) {
            return crypto.createCertificate(pemObject.content)
        }

        throw IllegalArgumentException("'resource' doesn't specify a valid certificate")
    }

    private fun loadBcPrivateKeyResource(resource: String): AsymmetricKeyParameter {
        val pemObject = loadPemResource(resource)

        return when (pemObject.type) {
            "PRIVATE KEY" -> PrivateKeyFactory.createKey(pemObject.content)
            "ENCRYPTED PRIVATE KEY" -> throw UnsupportedOperationException("Encrypted PKCS#8 keys not supported")
            "RSA PRIVATE KEY" -> {
                val rsaPrivateKey = RSAPrivateKey.getInstance(pemObject.content)
                RSAPrivateCrtKeyParameters(
                    rsaPrivateKey.modulus,
                    rsaPrivateKey.publicExponent,
                    rsaPrivateKey.privateExponent,
                    rsaPrivateKey.prime1,
                    rsaPrivateKey.prime2,
                    rsaPrivateKey.exponent1,
                    rsaPrivateKey.exponent2,
                    rsaPrivateKey.coefficient
                )
            }

            "EC PRIVATE KEY" -> {
                val ecPrivateKey = ECPrivateKey.getInstance(pemObject.content)
                val algorithmIdentifier = AlgorithmIdentifier(
                    X9ObjectIdentifiers.id_ecPublicKey,
                    ecPrivateKey.parametersObject.toASN1Primitive()
                )
                val privateKeyInfo = PrivateKeyInfo(algorithmIdentifier, ecPrivateKey)
                PrivateKeyFactory.createKey(privateKeyInfo)
            }

            else -> throw IllegalArgumentException("'resource' doesn't specify a valid private key")
        }
    }

    private fun loadJcaPrivateKeyResource(crypto: JcaTlsCrypto, resource: String): PrivateKey {
        val pemObject = loadPemResource(resource)

        return when (pemObject.type) {
            "PRIVATE KEY" -> loadJcaPkcs8PrivateKey(crypto, pemObject.content)
            "ENCRYPTED PRIVATE KEY" -> throw UnsupportedOperationException("Encrypted PKCS#8 keys not supported")
            "RSA PRIVATE KEY" -> {
                val rsaPrivateKey = RSAPrivateKey.getInstance(pemObject.content)
                val keyFactory = crypto.helper.createKeyFactory("RSA")
                keyFactory.generatePrivate(
                    RSAPrivateCrtKeySpec(
                        rsaPrivateKey.modulus,
                        rsaPrivateKey.publicExponent,
                        rsaPrivateKey.privateExponent,
                        rsaPrivateKey.prime1,
                        rsaPrivateKey.prime2,
                        rsaPrivateKey.exponent1,
                        rsaPrivateKey.exponent2,
                        rsaPrivateKey.coefficient
                    )
                )
            }

            else -> throw IllegalArgumentException("'resource' doesn't specify a valid private key")
        }
    }

    private fun loadJcaPkcs8PrivateKey(crypto: JcaTlsCrypto, encodedKey: ByteArray): PrivateKey {
        val privateKeyInfo = PrivateKeyInfo.getInstance(encodedKey)
        val algorithmIdentifier = privateKeyInfo.privateKeyAlgorithm

        val name = when (val algorithm = algorithmIdentifier.algorithm) {
            X9ObjectIdentifiers.id_dsa -> "DSA"
            X9ObjectIdentifiers.id_ecPublicKey -> "EC"
            PKCSObjectIdentifiers.rsaEncryption,
            PKCSObjectIdentifiers.id_RSASSA_PSS -> "RSA"

            EdECObjectIdentifiers.id_Ed25519 -> "Ed25519"
            EdECObjectIdentifiers.id_Ed448 -> "Ed448"
            else -> algorithm.id
        }

        val keyFactory = crypto.helper.createKeyFactory(name)
        return keyFactory.generatePrivate(PKCS8EncodedKeySpec(encodedKey))
    }

    private fun loadPemResource(resource: String): PemObject =
        PemReader(FileReader(resource)).use(PemReader::readPemObject)
}