package cn.lin1874.netty.dubborpc.provider;

import cn.lin1874.netty.dubborpc.common.HelloService;

/**
 * @author lin1874
 * @date 2021/5/31 - 22:17
 */
public class HelloServiceImpl implements HelloService {

    private static int count = 0;
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端的消息为：" + msg);
        if (msg != null) {
            return "你好客户端，我已经收到你的消息[" + msg + "]";
        } else {
            return "你好客户端，我已经收到你的消息";
        }
    }
}
