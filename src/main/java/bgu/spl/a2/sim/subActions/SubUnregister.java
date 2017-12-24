package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class SubUnregister extends Action<Boolean> {

    private String courseName;

    public SubUnregister(String courseName) {
        this.courseName = courseName;
    }

    @Override
    protected void start() {

        StudentPrivateState privateState = (StudentPrivateState) getPrivateState();

        if (privateState.getGrades().get(courseName) != null) {
            privateState.getGrades().remove(courseName);
            complete(true);
            return;
        } else {
            complete(false);
        }

    }
}
