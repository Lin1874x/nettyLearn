package cn.lin1874.netty.protocoltcp;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author lin1874
 * @date 2021/5/29 - 16:10
 */
public class NettyClient {
    public static void main(String[] args) throws Exception{
        //客户端的事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建客户端启动对象
            // 注意 客户端使用的不是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();
            //设置相关参数
            bootstrap.group(group) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MyMessageEncoder());
                            ch.pipeline().addLast(new MyMessageDecoder());
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
            System.out.println("客户端 is ready...");
            // 启动客户端去连接服务器
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",7000).sync();
            //监听关闭通道
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }
}
