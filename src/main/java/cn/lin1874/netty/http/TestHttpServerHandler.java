package cn.lin1874.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
 * HttpObject 客户端和服务器端相互通讯的数据被封装成HttpObject
 * @author lin1874
 * @date 2021/5/29 - 21:01
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    // channelRead0 读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是httprequest请求
        if (msg instanceof HttpRequest) {
            System.out.println("pipeline hashcode" + ctx.pipeline().hashCode()
             + " TestHttpServerHandler hashcode = " + this.hashCode());
            System.out.println("msg 类型 = " + msg.getClass());
            System.out.println("客户端地址 " + ctx.channel().remoteAddress());;
            HttpRequest httpRequest = (HttpRequest) msg;
            //获取uri，过滤指定资源
            URI uri = new URI(httpRequest.uri());
            if("/favicon.ico".equals(uri.getPath())) {
                System.out.println("你请求了favicon.ico,不做响应");
                return;
            }
            // 回复信息给浏览器
            ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器", CharsetUtil.UTF_8);
            //构造一个HttpResponse
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            //返回HttpResponse
            ctx.writeAndFlush(response);
        }
    }
}
