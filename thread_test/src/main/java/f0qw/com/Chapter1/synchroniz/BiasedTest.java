package f0qw.com.Chapter1.synchroniz;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

@Slf4j
public class BiasedTest {
    //-XX:BiasedLockingStartupDelay=0     禁用延迟
    //-XX:-UseBiasedLocking      禁用偏向锁
    //ClassLayout.parseInstance(dog).toPrintable() 打印对象头
    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();
        dog.hashCode(); //会禁用这个对象的偏向锁
        log.debug(ClassLayout.parseInstance(dog).toPrintable());
        synchronized (dog){
            log.debug(ClassLayout.parseInstance(dog).toPrintable());
        }
        log.debug(ClassLayout.parseInstance(dog).toPrintable()); //偏向锁
    }
}

class Dog{

}
