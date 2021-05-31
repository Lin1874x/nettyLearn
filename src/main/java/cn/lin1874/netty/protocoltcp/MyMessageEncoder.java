package cn.lin1874.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author lin1874
 * @date 2021/5/31 - 20:20
 */
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println("My MessageEncoder encode 方法被调用了...");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
    }
}
