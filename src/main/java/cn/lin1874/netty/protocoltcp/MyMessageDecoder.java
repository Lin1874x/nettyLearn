package cn.lin1874.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.sql.SQLOutput;
import java.util.List;

/**
 * @author lin1874
 * @date 2021/5/31 - 20:22
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("My MessageDecoder decode 方法被调用了...");
        int len = in.readInt();
        byte[] content = new byte[len];
        in.readBytes(content);
        //封装成MessageProtocal对象，放入out，传递给下一个handler处理
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setLen(len);
        messageProtocol.setContent(content);
        out.add(messageProtocol);

    }
}
