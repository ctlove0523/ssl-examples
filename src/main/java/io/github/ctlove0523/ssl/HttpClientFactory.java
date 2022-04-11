package io.github.ctlove0523.ssl;

public interface HttpClientFactory<T> {

    T getHttpClient();

    T getHttpsClient(KeyStoreProvider keyStoreProvider);
}
