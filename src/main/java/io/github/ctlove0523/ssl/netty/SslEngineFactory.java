package io.github.ctlove0523.ssl.netty;

import io.github.ctlove0523.ssl.ServerSslInfo;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLEngine;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

public class SslEngineFactory {

    public static SSLEngine createSSLEngine(ServerSslInfo serverSslInfo) throws Exception {
        InputStream cert = new ByteArrayInputStream(serverSslInfo.getServerCert());
        InputStream key = new ByteArrayInputStream(serverSslInfo.getServerKeyPks8());
        String pwd = new String(serverSslInfo.getServerKeyPks8Password());

        SslContextBuilder builder = SslContextBuilder.forServer(cert, key, pwd);
        return builder.build().newEngine(ByteBufAllocator.DEFAULT);
    }
}
