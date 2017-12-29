package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;

public class SubAdministrativeCheck extends Action<Boolean> {

    private ArrayList<String> courses;
    private SuspendingMutex mutex;

    /**
     * Constructor
     *
     * @param courses
     * @param mutex
     */
    public SubAdministrativeCheck(ArrayList<String> courses, SuspendingMutex mutex) {
        this.courses = courses;
        this.mutex = mutex;
    }

    /**
     * action
     */
    @Override
    protected void start() {


        StudentPrivateState privateState = ((StudentPrivateState) getPrivateState());

        Promise<Computer> promise = mutex.down();
        if (promise == null) {
            long sig = mutex.getComputer().checkAndSign(courses, privateState.getGrades());
            privateState.setSignature(sig);
            mutex.up();
            complete(true);
        } else {
            promise.subscribe(() -> {
                long sig = mutex.getComputer().checkAndSign(courses, privateState.getGrades());
                privateState.setSignature(sig);
                mutex.up();
                complete(true);
            });
        }


    }
}
