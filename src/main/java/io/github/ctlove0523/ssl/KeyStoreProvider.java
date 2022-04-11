package io.github.ctlove0523.ssl;

import java.security.KeyStore;

public interface KeyStoreProvider {
    KeyStore keyStore();

    char[] keyStorePassword();

    String keyStoreAlias();

    KeyStore trustStore();

    char[] trustStorePassword();

    String trustStoreAlias();
}
