package io.github.ctlove0523.ssl.okhttp;

import io.github.ctlove0523.ssl.HttpClientFactory;
import io.github.ctlove0523.ssl.KeyStoreProvider;
import io.github.ctlove0523.ssl.util.KeyStoreUtil;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.util.Objects;

public class OkHttpsClientFactory implements HttpClientFactory<OkHttpClient> {

    @Override
    public OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    public  OkHttpClient getHttpsClient(KeyStoreProvider keyStoreProvider) {
        KeyStore keyStore = keyStoreProvider.keyStore();
        KeyStore trustStore = keyStoreProvider.trustStore();

        SSLContext sslContext = KeyStoreUtil.createSslContext(keyStore, keyStoreProvider.keyStorePassword(), trustStore);
        X509TrustManager x509TrustManager = KeyStoreUtil.createX509TrustManager(trustStore);

        Objects.requireNonNull(sslContext, "sslContext");
        Objects.requireNonNull(x509TrustManager, "x509TrustManager");

        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), x509TrustManager)
                .build();
    }
}
