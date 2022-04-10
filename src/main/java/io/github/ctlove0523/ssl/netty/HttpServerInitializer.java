package io.github.ctlove0523.ssl.netty;

import io.github.ctlove0523.ssl.ServerSslInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ServerSslInfo serverSslInfo = new ServerSslInfo();
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("ssl", new SslHandler(SslEngineFactory.createSSLEngine(serverSslInfo)));
        pipeline.addLast("codec", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
        pipeline.addLast("req-handler", new HttpRequestHandler());
    }
}
