package cn.lin1874.nio;

import java.nio.IntBuffer;

/**
 * @author lin1874
 * @date 2021/5/28 - 9:55
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个大小为5的Buffer，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        intBuffer.put(10);
        intBuffer.put(20);
        intBuffer.put(30);
        intBuffer.put(40);
        intBuffer.put(50);
        //抛出异常
        //intBuffer.put(6);
//        for (int i = 0; i < intBuffer.capacity(); ++i) {
//            intBuffer.put(i * 10);
//        }
        //读写转换（必须要有
        intBuffer.flip();
        while(intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
