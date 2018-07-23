import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by johnswd on 7/20/2018.
 */
class GenerationBreeder implements Runnable {

    private final Lock lock = new ReentrantLock();
    private final Condition breedGeneration = lock.newCondition();
    private final Condition doneBreeding = lock.newCondition();
    private final SmartDots smartDots;

    private volatile boolean running;

    public GenerationBreeder( SmartDots smartDots ) {
        this.smartDots = smartDots;
        this.running = true;
    }

    @Override
    public void run() {
        while ( running ) {
            lock.lock();
            try {
                breedGeneration.await();
                smartDots.breedGeneration();
                doneBreeding.signal();
            } catch ( InterruptedException e ) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public synchronized void setRunning( boolean running ) {
        this.running = running;
    }

    public void getLock(){
        lock.lock();
    }

    public void releaseLock(){
        lock.unlock();
    }

    public void signalBreeding(){
        breedGeneration.signal();
    }

    public void waitForBreeding() throws InterruptedException {
        doneBreeding.await();
    }
}
