package cn.lin1874.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lin1874
 * @date 2021/5/30 - 10:21
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    //定义一个channel组来管理所有的channel
    //GlobalEventExecutor.INSTANCE是全局的事件执行器（单例）
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");

    //建立连接执行该方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush( sdf.format(new Date()) + "[客户端] " + channel.remoteAddress() + " 加入聊天 " + "\n");
        channelGroup.add(channel);
    }
    //断开连接，将客户离开信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(sdf.format(new Date()) + "[客户端] " + channel.remoteAddress() + "离开了。。。\n");
    }
    //表示channel处于活动状态，提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了~");
    }
    // 示channel处于非活动状态，提示xx下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了~");
    }
    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    //读取信息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
            if (channel != ch) {
                ch.writeAndFlush(sdf.format(new Date()) + channel.remoteAddress() + "[客户端] 发送了消息 ：" + msg + "\n");
            } else {
                ch.writeAndFlush(sdf.format(new Date()) + channel.remoteAddress() + "[自己] 发送了消息 ：" + msg + "\n");
            }
        });
    }
}
