package bgu.spl.a2;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {

    private ArrayList<Thread> threads;
    private HashMap<String, Queue<Action<?>>> actionMap;
    private HashMap<String, PrivateState> privateStateMap;
    private HashMap<String, AtomicBoolean> isTaken;
    private VersionMonitor vm;
    private boolean shutdown;


    /**
     * creates a {@link ActorThreadPool} which has nthreads. Note, threads
     * should not get started until calling to the {@link #start()} method.
     * <p>
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     *
     * @param nthreads the number of threads that should be started by this thread
     *                 pool
     */
    public ActorThreadPool(int nthreads) {
        //Init
        threads = new ArrayList<>(nthreads);
        actionMap = new HashMap<>();
        privateStateMap = new HashMap<>();
        isTaken = new HashMap<>();
        vm = new VersionMonitor();
        boolean shutdown = false;


        for (int i = 0; i < nthreads; i++) {
            threads.add(new Thread(() -> {
                try {
                    while (!shutdown) {
                        boolean foundAction = findAndExecute();

                        if (!foundAction) {
                            vm.await(vm.getVersion());
                        }

                    }//end while
                } catch (InterruptedException e) {
                    // ignore
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }));
        }
    }

    public synchronized boolean findAndExecute() {//TODO consider moving syncronized to less code
        try {
            boolean foundAction = false;
            for (Map.Entry<String, Queue<Action<?>>> entry : actionMap.entrySet()) {

                Queue<Action<?>> actorQueue = entry.getValue();
                String actorID = entry.getKey();

                if (!actorQueue.isEmpty()) {

                    if (isTaken.get(actorID).compareAndSet(false, true)) {

                        foundAction = true;
                        Action<?> currAction = actorQueue.poll();
                        if (currAction != null)
                            currAction.handle(ActorThreadPool.this, actorID, privateStateMap.get(actorID));//TODO BUG: nullpointerexception


                        isTaken.get(actorID).compareAndSet(true, false);
                    }
                }

            }//end foreach
            return foundAction;
        } catch (ConcurrentModificationException e) {
            // e.printStackTrace();
        }
        return false;

    }

    /**
     * getter for actionMap
     *
     * @return actionMap
     */
    public HashMap<String, Queue<Action<?>>> getActionsMap() {
        return actionMap;
    }


    /**
     * getter for actors
     *
     * @return actors
     */
    public HashMap<String, PrivateState> getPrivateStateMap() {
        return privateStateMap;
    }

    /**
     * getter for actor's private state
     *
     * @param actorId actor's id
     * @return actor's private state
     */
    public PrivateState getPrivateState(String actorId) {
        return privateStateMap.get(actorId);
    }


    /**
     * submits an action into an actor to be executed by a thread belongs to
     * this thread pool
     *
     * @param action     the action to execute
     * @param actorId    corresponding actor's id
     * @param actorState actor's private state (actor's information)
     */
    public void submit(Action<?> action, String actorId, PrivateState actorState) {
        //check if id exists
        if (actionMap.containsKey(actorId)) {
            actionMap.get(actorId).add(action);

        }

        //new id
        else {
            Queue<Action<?>> newActor = new LinkedList<>();
            newActor.add(action);
            actionMap.put(actorId, newActor);
            privateStateMap.put(actorId, actorState);
            isTaken.put(actorId, new AtomicBoolean(false));
        }

        vm.inc();
    }

    /**
     * closes the thread pool - this method interrupts all the threads and waits
     * for them to stop - it is returns *only* when there are no live threads in
     * the queue.
     * <p>
     * after calling this method - one should not use the queue anymore.
     *
     * @throws InterruptedException if the thread that shut down the threads is interrupted
     */
    public void shutdown() throws InterruptedException {
        this.shutdown = true;
        for (Thread thread : threads) {
            thread.interrupt();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }

    /**
     * start the threads belongs to this thread pool
     */
    public void start() {

        for (Thread thread : threads) {
            thread.start();
        }

    }

    public HashMap<String, AtomicBoolean> getIsTaken() {
        return isTaken;
    }

    public synchronized void addActor(String id, PrivateState privateState) {

        this.actionMap.put(id, new PriorityQueue<>());
        this.privateStateMap.put(id, privateState);
        this.isTaken.put(id, new AtomicBoolean(false));
    }


}
