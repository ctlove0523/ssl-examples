package io.github.ctlove0523.ssl.tomcat;

import io.github.ctlove0523.ssl.ServerSslInfo;
import io.github.ctlove0523.ssl.util.KeyStoreUtil;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.SslStoreProvider;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.security.KeyStore;

@Component
public class TomcatSsl implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    private static final String[] CIPHERS = new String[]{"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA",
            "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA",
            "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384",
            "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384"};

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        ServerSslInfo serverSslInfo = new ServerSslInfo();
        factory.setSsl(createSsl(serverSslInfo));
        factory.setSslStoreProvider(createSslStoreProvider(serverSslInfo));
    }

    private Ssl createSsl(ServerSslInfo serverSslInfo) {
        Ssl ssl = new Ssl();
        ssl.setEnabled(true);
        ssl.setClientAuth(Ssl.ClientAuth.NEED);

        ssl.setProtocol("TLS");
        ssl.setEnabledProtocols(new String[]{"TLSv1.2"});
        ssl.setCiphers(CIPHERS);

        ssl.setKeyAlias(serverSslInfo.getKeyStoreAlias());
        ssl.setKeyPassword(new String(serverSslInfo.getKeyStorePassword()));

        return ssl;
    }

    private SslStoreProvider createSslStoreProvider(ServerSslInfo serverSslInfo) {
        return new SslStoreProvider() {
            @Override
            public KeyStore getKeyStore() throws Exception {
                return KeyStoreUtil.createKeyStore(serverSslInfo.getKeyStore(), serverSslInfo.getKeyStorePassword());
            }

            @Override
            public KeyStore getTrustStore() throws Exception {
                return KeyStoreUtil.createKeyStore(serverSslInfo.getTrustStore(), serverSslInfo.getTrustStorePassword());
            }
        };
    }
}
