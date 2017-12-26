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

    public SubAdministrativeCheck(ArrayList<String> courses, SuspendingMutex mutex) {
        this.courses = courses;
        this.mutex = mutex;
    }

    @Override
    protected void start() {


        StudentPrivateState privateState = ((StudentPrivateState) getPrivateState());

        Promise<Computer> promise = mutex.down();
        if (promise == null) {
            System.out.println("#########1");
            long sig = mutex.getComputer().checkAndSign(courses, privateState.getGrades());
            privateState.setSignature(sig);
            mutex.up();
            complete(true);
        } else {
            System.out.println("#########2");
            promise.subscribe(() -> {
                System.out.println("#########3");
                long sig = mutex.getComputer().checkAndSign(courses, privateState.getGrades());
                privateState.setSignature(sig);
                mutex.up();
                complete(true);
            });
        }


    }
}
