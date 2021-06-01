package cn.lin1874.netty.dubborpc.netty;

import cn.lin1874.netty.dubborpc.consumer.ClientBootstrap;
import cn.lin1874.netty.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author lin1874
 * @date 2021/5/31 - 22:29
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg为：" + msg);
        //(自定义协议)客户端调用服务时，发送的消息必须以HelloService#hello#XXX开头
        if (msg.toString().startsWith(ClientBootstrap.providerName)) {
            String res = new HelloServiceImpl().hello(
                    msg.toString().substring(msg.toString().lastIndexOf("#") + 1)
            );
            ctx.writeAndFlush(res);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
