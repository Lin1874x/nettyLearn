package cn.lin1874.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 创建ServerSocketChannel来监听客户端连接
 * 创建Selector来监听Channel中是否有事件发生
 * 将ServerSocketChannel注册到Selector进行监听
 * 循环等待事件发生
 * 如果无事件发生继续等待
 * 有事件发生：获取所有SelectorKeys集合并遍历，查找发生事件的key，处理事件
 *
 * @author lin1874
 * @date 2021/5/28 - 17:14
 */
public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //创建一个Selector
        Selector selector = Selector.open();
        //绑定一个端口6666，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel注册到Selector   事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while(true) {
            //等待1秒，如果没有事件发生，就继续等待连接
            if (selector.select(1000) == 0) {//没有事件发生
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回 > 0，就获取到相关的selectionKey集合
            //通过selector.selectedKeys()返回关注事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while(keyIterator.hasNext()) {
                //获取到SelectionKey
                SelectionKey key = keyIterator.next();
                //根据key对应的通道发生的事件做相应的事件
                if (key.isAcceptable()) { // 如果是OP_ACCEPT,有新的客户端连接进来
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功,生成了一个socketChannel = " + socketChannel.hashCode());
                    //将SocketChannel设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel注册到Selector，关注事件为OP_READ，同时给socketChannel关联一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()) { //发生OP_READ
                    //通过key反向获取对应的channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("接收从客户端发来的消息 ：[" + new String(buffer.array()) + "]");
                }
                //手动从集合中移除当前的selectionKey，防止重复操作
                keyIterator.remove();
            }

        }

    }
}
