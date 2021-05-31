package cn.lin1874.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * TCP实例
 * 服务器监听6668端口，客户端能发送消息给服务器
 * 服务器可以回复消息给客户端
 *
 * @author lin1874
 * @date 2021/5/29 - 15:53
 */
public class NettyServer {
    public static void main(String[] args) throws Exception{
        /**
         * 创建BossGroup和WorkerGroup
         * bossGroup只是处理连接请求，真正的和客户端业务处理，会交给workerGroup处理
         * 两个都是无限循环
         * bossGroup和workerGroup含有的子线程（NioEventLoop）的个数默认实际cpu核数*2
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG,128) // 设置线程队列得到的连接个数
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 创建通道初始化对象（匿名对象）
                        //给pipeline设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                        }
                    }); // 给我们的workerGroup的EventLoop对应的管道设置处理器
            System.out.println("...服务器 is ready...");
            //启动服务器，绑定一个端口，并且同步，生成了一个ChannelFuture对象
            ChannelFuture cf = bootstrap.bind(6668).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
