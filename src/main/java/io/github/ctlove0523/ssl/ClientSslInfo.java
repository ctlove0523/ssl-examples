package io.github.ctlove0523.ssl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSslInfo {
    private byte[] keyStore;
    private char[] keyStorePassword;
    private String keyStoreAlias;
    private byte[] trustStore;
    private char[] trustStorePassword;
    private String trustStoreAlias;
}
