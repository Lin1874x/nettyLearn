package cn.lin1874.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个buffer完成文件的读取
 * @author lin1874
 * @date 2021/5/28 - 11:06
 */
public class NIOFileChannel03{
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("file01.txt");
        FileChannel channel01 = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");
        FileChannel channel02 = fileOutputStream.getChannel();
        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        while (true) {
            //清空buffer
            byteBuffer.clear();
            //获取读取到的长度 -1 结束
            int read = channel01.read(byteBuffer);
            if (read == -1) break;
            byteBuffer.flip();
            channel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
    public void fistVersionByMyself() throws Exception{
        File file = new File("file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);
        //System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();

        //写入file02.txt
        FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");
        FileChannel channel1 = fileOutputStream.getChannel();
        byteBuffer.flip();
        channel1.write(byteBuffer);

        fileOutputStream.close();
    }
}
