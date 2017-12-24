package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class SubAddStudent extends Action<String> {

    private long signature;

    public SubAddStudent(long signature) {
        this.signature = signature;
    }

    @Override
    protected void start() {

        ((StudentPrivateState) getPrivateState()).setSignature(signature);
        complete("Student " + signature + " added successfully");
    }
}
