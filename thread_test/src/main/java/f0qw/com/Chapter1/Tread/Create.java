package f0qw.com.Chapter1.Tread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j(topic = "c.Test1")
public class Create {
    public static void main(String[] args) {
//        function1();
//        function2();
        function3();
    }

    /**
     *   第三种创建线程的方式,
     *   可以从线程运行中返回运行结果
     */
    private static void function3() {
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("running3 ...");
                Thread.sleep(100000);
                return 300;
            }
        });
        Thread t = new Thread(task,"t1");
        t.start();
        Integer res = null;
        try {
            //一直阻塞等待到结果返回,即这里等了一秒
            res = task.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        log.debug("{}",res);
    }

    /**
     * 创建线程的第二种方式,
     * runnable把线程和任务分开了,更推荐使用
     */
    private static void function2() {
//        Runnable r = new Runnable() {
//            public void run() {
//                log.debug("running 2");
//            }
//        };

        //可以使用lambda表达式简化
        Runnable r = ()-> log.debug("running 2") ;

        Thread t = new Thread(r, "t2");
        t.start();
    }

    /**
     * 创建线程的第一种方式
     */
    private static void function1() {
        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
        log.debug("running");
    }
}
