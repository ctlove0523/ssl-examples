package io.github.ctlove0523.ssl.apache;

import io.github.ctlove0523.ssl.HttpClientFactory;
import io.github.ctlove0523.ssl.KeyStoreProvider;
import org.apache.hc.client5.http.DnsResolver;
import org.apache.hc.client5.http.SystemDefaultDnsResolver;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.pool.PoolConcurrencyPolicy;
import org.apache.hc.core5.pool.PoolReusePolicy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.TimeValue;

import javax.net.ssl.SSLContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.Objects;

public class ApacheHttpsClientFactory implements HttpClientFactory<CloseableHttpClient> {

    @Override
    public CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    @Override
    public CloseableHttpClient getHttpsClient(KeyStoreProvider keyStoreProvider) {
        try {
            KeyStore keyStore = keyStoreProvider.keyStore();
            KeyStore trustStore = keyStoreProvider.trustStore();
            SSLContextBuilder builder = SSLContexts.custom()
                    .loadTrustMaterial(trustStore, (chain, authType) -> true);
            if (Objects.nonNull(keyStore)) {
                builder.loadKeyMaterial(keyStore, keyStoreProvider.keyStorePassword());
            }
            SSLContext sslContext = builder.build();

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();


            DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

                @Override
                public InetAddress[] resolve(final String host) throws UnknownHostException {
                    if (host.equalsIgnoreCase("myhost")) {
                        return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
                    } else {
                        return super.resolve(host);
                    }
                }

            };
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry, PoolConcurrencyPolicy.STRICT, PoolReusePolicy.LIFO, TimeValue.ofMinutes(5),
                    null, dnsResolver, null);

            return HttpClients.custom()
                    .setConnectionManager(connManager)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
