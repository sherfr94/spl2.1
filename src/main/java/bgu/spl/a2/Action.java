package bgu.spl.a2;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 * <p>
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the action result type
 */
public abstract class Action<R> {

    //All Actions
    private ActorThreadPool pool;

    //Current Action's Actor
    private String actorId;
    private PrivateState actorState;

    //Current Action
    private callback callback;
    private boolean isStarted = false;
    private String actionName;
    private Promise<R> promise = new Promise<>();

    /**
     * start handling the action - note that this method is protected, a thread
     * cannot call it directly.
     */
    protected abstract void start();
    //SHOULD BE IN EACH SPECIFIC ACITON - DON'T DO ANYTHING HERE

    /**
     * start/continue handling the action
     * <p>
     * this method should be called in order to start this action
     * or continue its execution in the case where it has been already started.
     * <p>
     * IMPORTANT: this method is package protected, i.e., only classes inside
     * the same package can access it - you should *not* change it to
     * public/private/protected
     */

    final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {

        //Start
        if (!isStarted) {
            this.pool = pool;
            this.actorId = actorId;
            this.actorState = actorState;

            isStarted = true;
            start();

            //Continue
        } else {
            this.callback.call();
            //TODO Sometimes result is null - WHY
        }

    }


    /**
     * add a callback to be executed once *all* the given actions results are
     * resolved
     * <p>
     * Implementors note: make sure that the callback is running only once when
     * all the given actions completed.
     *
     * @param actions  to complete before calling callback
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void then(Collection<? extends Action<?>> actions, callback callback) {
        this.callback = callback;
        CountDownLatch countRemainingActions = new CountDownLatch(actions.size());

        for (Action<?> a : actions) {

            //Decrease count when resolved
            a.getResult().subscribe(() -> {
                if (a.getResult().isResolved())
                    countRemainingActions.countDown();
            });
        }

        //Add back current action to original actor
        pool.submit(this, this.actorId, this.actorState);

    }

    /**
     * resolve the internal result - should be called by the action derivative
     * once it is done.
     *
     * @param result - the action calculated result
     */
    protected final void complete(R result) {
        this.promise.resolve(result);
        //TODO understand what R result is when resolving

    }

    /**
     * @return action's promise (result)
     */
    public final Promise<R> getResult() {
        return promise;
    }

    /**
     * send an action to an other actor
     *
     * @param action     the action
     * @param actorId    actor's id
     * @param actorState actor's private state (actor's information)
     * @return promise that will hold the result of the sent action
     */
    public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState) {
        this.pool.submit(action, actorId, actorState);
        this.pool.getIsTaken().get(this.actorId).compareAndSet(true, false);//TODO check if correct
        return action.getResult();
    }

    /**
     * @return action's name
     */
    public String getActionName() {
        return actionName;
    }

    /**
     * set action's name
     *
     * @param actionName set
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActorId() {
        return actorId;
    }

    public PrivateState getPrivateState() {
        return actorState;
    }

    public ActorThreadPool getPool() {
        return pool;
    }
}
