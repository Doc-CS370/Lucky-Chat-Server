package com.chat.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class DrLuckyClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public DrLuckyClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();

        cp.addLast(sslCtx.newHandler(ch.alloc(), DrLuckyClient.HOST, DrLuckyClient.PORT));

        // On top of the SSL handler, add the text line codec.
        cp.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        cp.addLast(new StringDecoder());
        cp.addLast(new StringEncoder());

        // and then business logic.
        cp.addLast(new DrLuckyClientHandler());
    }
}
