package cn.lin1874.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * @author lin1874
 * @date 2021/5/29 - 20:53
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        //向管道加入处理器
        //获得管道
        ChannelPipeline pipeline = sc.pipeline();
        //加入一个netty提供的HttpServerCodec
        //HttpServerCodec是netty提供的处理http的编/解码器
        pipeline.addLast("MyHttpServerCodec", new HttpServerCodec());
        //增加一个自定义的handler
        pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());

    }
}
