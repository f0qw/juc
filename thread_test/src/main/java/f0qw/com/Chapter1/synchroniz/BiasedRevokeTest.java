package f0qw.com.Chapter1.synchroniz;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 使用了 -XX:BiasedLockingStartupDelay=0     禁用延迟
 * 一开始偏向锁偏向t1,后面加了轻量级锁,解锁后变成不可偏向的normal状态
 */
@Slf4j
public class BiasedRevokeTest {
    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();

        new Thread(()->{
            log.debug(ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog){
                log.debug(ClassLayout.parseInstance(dog).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(dog).toPrintable());
            synchronized (BiasedRevokeTest.class){
                BiasedRevokeTest.class.notify();
            }
        },"t1").start();

        new Thread(()->{
            synchronized (BiasedRevokeTest.class){
                try {
                    BiasedRevokeTest.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(ClassLayout.parseInstance(dog).toPrintable());
            synchronized (dog){
                log.debug(ClassLayout.parseInstance(dog).toPrintable());
            }
            log.debug(ClassLayout.parseInstance(dog).toPrintable());

        },"t2").start();

    }
}
