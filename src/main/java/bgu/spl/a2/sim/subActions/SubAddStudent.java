package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class SubAddStudent extends Action<String> {

    private String studentID;

    public SubAddStudent(String studentID) {
        this.studentID = studentID;
    }

    @Override
    protected void start() {

        ((StudentPrivateState) getPrivateState()).setSignature(911);//TODO change signature to computer sigfail/success
        complete("Student " + studentID + " added successfully");
    }
}
