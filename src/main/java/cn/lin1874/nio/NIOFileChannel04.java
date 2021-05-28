package cn.lin1874.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 实现文件的拷贝
 * 通过transferFrom将一个通道复制到另一个通道
 * @author lin1874
 * @date 2021/5/28 - 11:30
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("file01.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("file03.txt");

        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        outputStreamChannel.transferFrom(inputStreamChannel,0,inputStreamChannel.size());

        fileInputStream.close();
        fileOutputStream.close();

    }
}
