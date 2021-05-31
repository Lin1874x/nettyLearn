package cn.lin1874.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author lin1874
 * @date 2021/5/31 - 20:05
 */
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer);
        System.out.println("服务器接收到的信息：" + message);
        System.out.println("服务器接收到的信息的数量：" + (++count));
        ByteBuf buf = Unpooled.copiedBuffer(UUID.randomUUID().toString() + " ", CharsetUtil.UTF_8);
        ctx.writeAndFlush(buf);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
