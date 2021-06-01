package cn.lin1874.netty.dubborpc.consumer;

import cn.lin1874.netty.dubborpc.common.HelloService;
import cn.lin1874.netty.dubborpc.netty.NettyClient;


/**
 * @author lin1874
 * @date 2021/5/31 - 22:52
 */
public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws Exception{
        NettyClient consumer = new NettyClient();
        HelloService service = (HelloService) consumer.getBean(HelloService.class,providerName);
        for (;;) {
            Thread.sleep(2 * 1000);
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res = " + res);
        }
    }
}
