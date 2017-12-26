package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import bgu.spl.a2.sim.subActions.SubUnregister;

import java.util.ArrayList;

public class Unregister extends Action<String> {

    private String studentID;

    public Unregister(String studentID) {
        this.studentID = studentID;
    }

    @Override
    protected void start() {
        //TODO BUG: freeze

        //COURSE

        CoursePrivateState privateState = (CoursePrivateState) getPrivateState();
        SubUnregister subUnregister = new SubUnregister(getActorId());
        ArrayList<Action<?>> actions = new ArrayList<>();
        actions.add(subUnregister);

        sendMessage(subUnregister, studentID, new StudentPrivateState());

        then(actions, () -> {

            if (subUnregister.getResult().get()) {
                privateState.getRegStudents().remove(studentID);
                privateState.setAvailableSpots(privateState.getAvailableSpots() + 1);
                privateState.setRegistered(privateState.getRegistered() - 1);
                complete("Unregister success: \tStudent " + studentID + " unregistered from course: " + getActorId());
                System.out.println(getResult().get());
            } else {
                complete("Unregister fail: \tStudent wasn't registered");
                System.out.println(getResult().get());
            }
        });


    }
}
