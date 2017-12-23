package bgu.spl.a2.sim;

import bgu.spl.a2.Promise;

import java.util.Queue;

/**
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * <p>
 * Note: this class can be implemented without any synchronization.
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 */
public class SuspendingMutex {

    private boolean isFree;
    private Computer computer;
    private Queue<Promise<Computer>> promiseQueue;

    /**
     * Constructor
     *
     * @param computer
     */
    public SuspendingMutex(Computer computer) {
        this.isFree = true;
        this.computer = computer;
    }

    /**
     * Computer acquisition procedure
     * Note that this procedure is non-blocking and should return immediatly
     *
     * @return a promise for the requested computer
     */
    public Promise<Computer> down() {

        if (!this.isFree) {
            this.isFree = false;

        } else {
            Promise<Computer> promise = new Promise<>();
            promise.subscribe(() -> {
                down();
            });
            return promise;

        }
        return null;
    }

    /**
     * Computer return procedure
     * releases a computer which becomes available in the warehouse upon completion
     */
    public void up() {
        this.isFree = true;
        promiseQueue.poll().resolve(computer);

    }
}
