package io.github.ctlove0523.ssl.util;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class KeyStoreUtil {

    public static KeyStore createKeyStore(byte[] cert, char[] password) {
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new ByteArrayInputStream(cert), password);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            e.printStackTrace();
        }

        return keyStore;
    }

    public static SSLContext createSslContext(KeyStore keyStore, char[] keyStorePassword, KeyStore trustStore) {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("SSL");
            if (keyStore != null) {
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, "serverJKS@2022".toCharArray());
                sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            } else {
                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            }

            return sslContext;
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static X509TrustManager createX509TrustManager(KeyStore trustStore) {
        try {
            TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            factory.init(trustStore);
            return (X509TrustManager) factory.getTrustManagers()[0];
        } catch (NoSuchAlgorithmException | KeyStoreException e) {
            e.printStackTrace();
        }

        return null;
    }
}
