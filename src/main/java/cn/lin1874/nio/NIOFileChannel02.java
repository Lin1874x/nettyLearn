package cn.lin1874.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel读取文件数据
 * @author lin1874
 * @date 2021/5/28 - 10:46
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception{
        //创建输入流
        File file = new File("file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        //通过输入流获取FileChannel
        FileChannel channel = fileInputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        //将通道中的数据读入到缓冲区中
        channel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();

    }
}
