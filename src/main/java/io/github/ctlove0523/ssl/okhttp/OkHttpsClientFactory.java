package io.github.ctlove0523.ssl.okhttp;

import io.github.ctlove0523.ssl.ClientSslInfo;
import io.github.ctlove0523.ssl.util.KeyStoreUtil;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.util.Objects;

public class OkHttpsClientFactory {

    public static OkHttpClient createOkHttpClient(ClientSslInfo clientSslInfo) {
        KeyStore keyStore = KeyStoreUtil.createKeyStore(clientSslInfo.getKeyStore(), clientSslInfo.getKeyStorePassword());
        KeyStore trustStore = KeyStoreUtil.createKeyStore(clientSslInfo.getTrustStore(), clientSslInfo.getTrustStorePassword());

        SSLContext sslContext = KeyStoreUtil.createSslContext(keyStore, trustStore);
        X509TrustManager x509TrustManager = KeyStoreUtil.createX509TrustManager(trustStore);

        Objects.requireNonNull(sslContext, "sslContext");
        Objects.requireNonNull(x509TrustManager, "x509TrustManager");

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                .build();
    }

    public static OkHttpClient wrap(OkHttpClient client, ClientSslInfo clientSslInfo) {
        if (Objects.isNull(clientSslInfo)) {
            return client;
        }

        KeyStore keyStore = KeyStoreUtil.createKeyStore(clientSslInfo.getKeyStore(), clientSslInfo.getKeyStorePassword());
        KeyStore trustStore = KeyStoreUtil.createKeyStore(clientSslInfo.getTrustStore(), clientSslInfo.getTrustStorePassword());

        SSLContext sslContext = KeyStoreUtil.createSslContext(keyStore, trustStore);
        X509TrustManager x509TrustManager = KeyStoreUtil.createX509TrustManager(trustStore);

        Objects.requireNonNull(sslContext, "sslContext");
        Objects.requireNonNull(x509TrustManager, "x509TrustManager");

        return client.newBuilder()
                .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                .build();
    }
}
