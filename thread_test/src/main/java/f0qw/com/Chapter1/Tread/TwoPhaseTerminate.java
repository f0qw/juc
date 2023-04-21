package f0qw.com.Chapter1.Tread;

import lombok.extern.slf4j.Slf4j;

/**
 * 两阶段终止模式,
 * 在一个线程t1中如何优雅的终止线程t2?
 */
@Slf4j
public class TwoPhaseTerminate {
    public static void main(String[] args) throws InterruptedException {
        Monitor twoParseTermination = new Monitor();
        twoParseTermination.start();
        Thread.sleep(3000);  // 让监控线程执行一会儿
        twoParseTermination.stop(); // 停止监控线程
    }
}

/**
 * 监视器类,每隔一定时间执行监事记录,
 * 通过stop方法优雅的停止
 */
@Slf4j
class Monitor{
    Thread thread ;
    public void start(){
        thread = new Thread(()->{
            while(true){
                if (Thread.currentThread().isInterrupted()){
                    log.debug("线程结束。。正在料理后事中");
                    break;
                }
                try {
                    Thread.sleep(500);
                    log.debug("正在执行监控的功能");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public void stop(){
        thread.interrupt();
    }
}
