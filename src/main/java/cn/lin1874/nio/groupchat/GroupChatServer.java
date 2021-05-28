package cn.lin1874.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lin1874
 * @date 2021/5/28 - 19:31
 */
public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    //初始化
    public GroupChatServer() {
        try {
            //获得选择器
            selector = Selector.open();
            //获得ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将channel注册到selector中
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //监听
    public void listen() {
        try {
            while(true) {
                int count = selector.select(3000);
                if (count > 0) { // 有事件处理
                    //遍历selectionKeys集合
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //判断事件是否为accept
                        if (key.isAcceptable()) {
                            // 创建socketChannel
                            SocketChannel socketChannel = listenChannel.accept();
                            //设置非阻塞模式
                            socketChannel.configureBlocking(false);
                            //注册到selector中，监听OP_READ
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " :上线...");
                        }
                        //判断事件是否为read
                        if (key.isReadable()) {
                            //处理事件read
                            readData(key);
                        }
                        //删除当前的key，防止重复操作
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //读取客户端信息
    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            //获得channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //将channel中读入到byteBuffer
            int count = channel.read(byteBuffer);
            // 判断是否有读取到
            if (count > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("客户端发来数据：" + msg);
                //发送给其他客户端
                sendInfoToOtherClient(msg,channel);
            }

        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " : 离线了...");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }
    //转发消息给其他客户端
    public void sendInfoToOtherClient(String msg,SocketChannel channel) throws IOException{
        System.out.println("服务器转发消息中...");
        //遍历所有注册到selector上的socketChannel，并排除自己
        for (SelectionKey key: selector.keys()) {
            Channel targetChannel = (Channel) key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != channel) {
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
