package cn.lin1874.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

/**
 * 自定义Handler，需要继承netty规定好的某个HandlerAdapter（规范）
 * @author lin1874
 * @date 2021/5/29 - 16:04
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    // 读取客户端发送的消息
    //其中ChannelHandlerContext ctx 是上下文对象，含有 管道pipeline，通道channel，地址
    // Object msg 就是客户端发送的数据
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看chanel 和pipeline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); // 本质是一个双向链表，出站入栈
        // ByteBuf是Netty提供的，不是NIO的ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是： " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是： " + channel.remoteAddress());

    }
    //数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //writeAndFlush 是write + flush
        //将数据写入到缓存并刷新
        // 一般都需要对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端~",CharsetUtil.UTF_8));
    }
    // 异常处理， 一般是需要关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
