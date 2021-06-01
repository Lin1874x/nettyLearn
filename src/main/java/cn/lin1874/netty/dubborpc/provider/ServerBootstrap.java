package cn.lin1874.netty.dubborpc.provider;

import cn.lin1874.netty.dubborpc.netty.NettyServer;

/**
 * @author lin1874
 * @date 2021/5/31 - 22:19
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("127.0.0.1",7000);

    }
}
