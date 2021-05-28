package cn.lin1874.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author lin1874
 * @date 2021/5/28 - 17:52
 */
public class NIOClient {
    public static void main(String[] args) throws Exception{
        //创建一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while(!socketChannel.finishConnect()) {
                System.out.println("因为连接需要事件，客户端不会阻塞，可以做其他事件。。。");
            }
        }
        // 如果连接成功，就发送数据
        String str = "hello,world!你好。";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        //发送数据，将buffer写入channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
