import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MyActiveObject {

    BlockingQueue<Runnable> queue;
    volatile boolean stop;
    Thread worker;

    public MyActiveObject() {
        queue = new ArrayBlockingQueue<>(100);
        worker = new Thread(()-> {
            while(!stop) {
                try {
                    queue.take().run();
                } catch (InterruptedException e) {}
            }
        });

        worker.start();
    }

    public void execute(Runnable task) throws InterruptedException {
        queue.put(task);
    }

    // we want to let the tasks finish and only then stop, so we simply insert a stop task.
    public void shutDown() {
        try {
            execute(()-> stop=true);
        } catch (InterruptedException ex) { }
    }

    //immediate stop - even for running threads..
    public void shutDownNow() {
        stop = true;
        worker.interrupt();
    }
}
