package com.github.shandongdong.ssm.juc;

import lombok.Data;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-29 19:59
 * @Email: shandongdong@126.com
 * @Description: 内存可见性. volatile关键字：当多个线程操作共享数据时，可以保证内存中的数据可见。
 * 注意：volatile 不能保证原子性、不具备“互斥性”
 **/
public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();

        new Thread(threadDemo).start();
        // while(true)执行效率特别高，所以还没等其他线程执行完，他就已经开始执行了
        while (true) {
            if (threadDemo.isFlag()) {
                System.out.println("=======================> over");
                break;
            }
        }

    }
}

@Data
class ThreadDemo implements Runnable {
    // 多个线程操作共享数据，默认jvm为了提高性能会为每个线程单独分配内存。volatile关键字时多个线程访问的都是主存里面的数据，而不访问各自线程自己的缓存数据，所以不管哪个线程对变量进行操作，都会直接生效。
    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            // 这里故意让线程执行的慢一点
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag ===>" + flag);
    }
}
