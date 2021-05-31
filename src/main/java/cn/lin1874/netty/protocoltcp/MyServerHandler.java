package cn.lin1874.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @author lin1874
 * @date 2021/5/31 - 20:19
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();
        System.out.println();
        System.out.println();
        System.out.println("服务器接收到的信息如下");
        System.out.println("长度为：" + len);
        System.out.println("内容为：" + new String(content, CharsetUtil.UTF_8));
        System.out.println("服务器接收到消息包的数量为：" + (++count));

        //回复消息
        String str = UUID.randomUUID().toString();
        int responseLen = str.getBytes("utf-8").length;
        byte[] responseContent = str.getBytes("utf-8");
        //构建一个协议包发送
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(responseLen);
        messageProtocol.setContent(responseContent);
        ctx.writeAndFlush(messageProtocol);

    }
}
