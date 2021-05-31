package cn.lin1874.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @author lin1874
 * @date 2021/5/31 - 20:13
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler active!..");
        for (int i = 0; i < 5; ++i) {
            String msg = "今天天气不错，你好呀！";
            byte[] content = msg.getBytes(CharsetUtil.UTF_8);
            int length = msg.getBytes(CharsetUtil.UTF_8).length;
            //创建协议包对象
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(content);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println("客户端接收到的信息如下");
        System.out.println("长度为：" + len);
        System.out.println("内容为：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("客户端接收信息的数量为："  + (++count));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
