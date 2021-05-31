package cn.lin1874.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author lin1874
 * @date 2021/5/31 - 19:59
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; ++i) {
            ByteBuf buf = Unpooled.copiedBuffer("hello,server" + i, CharsetUtil.UTF_8);
            ctx.writeAndFlush(buf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msg) throws Exception {
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String message = new String(buffer);
        System.out.println("客户端接收到的信息：" + message);
        System.out.println("客户端接收到的信息的数量：" + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
