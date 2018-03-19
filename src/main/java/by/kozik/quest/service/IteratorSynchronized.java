package by.kozik.quest.service;

import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Serge on 14.01.2017.
 */
public class IteratorSynchronized<T> {
    private Iterator<T> iterator;
    private ReentrantLock lock;

    public IteratorSynchronized(Iterator<T> iterator) {
        this.iterator = iterator;
        lock = new ReentrantLock();
    }

    public T next() {
        lock.lock();
        T result = null;
        try {
             result = iterator.next();
        } catch (NoSuchElementException e) {
            result = null;
        }
        lock.unlock();
        return result;
    }
}
