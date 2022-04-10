package io.github.ctlove0523.ssl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerSslInfo {
    private byte[] keyStore;
    private char[] keyStorePassword;
    private String keyStoreAlias;
    private byte[] trustStore;
    private char[] trustStorePassword;
    private String trustStoreAlias;

    // pem格式的证书
    private byte[] serverCert;
    private byte[] serverKeyPks8;
    private char[] serverKeyPks8Password;
}
