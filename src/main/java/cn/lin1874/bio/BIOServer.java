package cn.lin1874.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lin1874
 * @date 2021/5/28 - 8:58
 */
public class BIOServer {

    public static void main(String[] args) throws Exception{

        //创建线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了！");
        while(true) {
            //监听，等待客户端连接
            final Socket socket = serverSocket.accept();
            System.out.println("连接到一个客户端!");
            //创建一个线程，与之通信
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //和客户端通信
                    handler(socket);
                }
            });
        }

    }
    //编写一个handler方法，和客户端通讯
    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //通过socket 获取输入流
            InputStream inputStream = socket.getInputStream();
            while(true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes,0,read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭和client连接");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
