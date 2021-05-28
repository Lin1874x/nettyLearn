package cn.lin1874.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel 将数据写入到文件
 * @author lin1874
 * @date 2021/5/28 - 10:33
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello,world!你好！";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("file01.txt");
        //获取fileChannel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //将str中的数据放入到缓冲区中
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        //翻转缓冲区的读写
        byteBuffer.flip();
        //将byteBuffer数据写入
        fileChannel.write(byteBuffer);
        fileOutputStream.close();

    }
}
