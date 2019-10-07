package com.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;


public class DrLuckyServerHandler extends SimpleChannelInboundHandler<String> {
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                (GenericFutureListener<Future<Channel>>) future -> {
                    ctx.writeAndFlush(
                            "Welcome to the Doctor Lucky chat!\n");
                    ctx.writeAndFlush(
                            "Your session is protected by " +
                                    ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite() +
                                    " cipher suite.\n");

                    channels.add(ctx.channel());
                });
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("User has said something: " + msg);
        //Loop through all connected channels
        for (Channel c: channels) {
            if(msg.equalsIgnoreCase("pong")){
                c.writeAndFlush("[ SERVER ]: PING \n");
            } else {
                if (c != ctx.channel()) {
                    c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
                } else {
                    //If its the channel that sent the message.
                    c.writeAndFlush("[you] " + msg + '\n');
                }
            }
        }

        // Close the connection if the client has sent 'bye'.
        if ("bye".equals(msg.toLowerCase())) {
            ctx.close();
        }
    }
}