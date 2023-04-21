package f0qw.com.Chapter1.Tread;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

@Slf4j
public class InterruptTutorial {
    public static void main(String[] args) throws InterruptedException {
//        interruptUsage();
        interruptAndPark();
    }


    private static void interruptAndPark() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：" + Thread.currentThread().isInterrupted());//打断状态：true

            LockSupport.park();//失效，不会阻塞,除非是用Thread.interrupt()
            log.debug("unpark...");//和上一个unpark同时执行
        }, "t1");
        t1.start();
        Thread.sleep(2000);
        t1.interrupt();

        Thread.sleep(3000);
        log.debug("三秒后又一次打断");//只对Thread.interrupt()有效
        t1.interrupt();
    }

    private static void interruptUsage() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("线程任务执行");
                try {
                    Thread.sleep(10000); // wait, join
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                    log.debug("被打断");
                }
            }
        };
        t1.start();
        Thread.sleep(500);
        log.debug("111是否被打断？{}",t1.isInterrupted());//false

        t1.interrupt();
        log.debug("222是否被打断？{}",t1.isInterrupted());//true

//        Thread.sleep(500);
        log.debug("333是否被打断？{}",t1.isInterrupted());//false

        //刚被中断完之后t1.isInterrupted()的值为true，后来变为false，
        // 即中断状态会被清除。那么线程是否被中断过可以通过异常来判断。
        log.debug("主线程");
    }
}
