public class MyFuture<V> implements Future<V> {

    private V value;

    public synchronized void set(V value) {
        this.value = value;
        notifyAll();
    }

    @Override
    public V get() {
        if(value == null) {
            synchronized(this) {
            try {
                if (value == null) {
                wait();
                }
            } catch (InterruptedException e) {}
        }
    }
        return value;
    }
    
}
