package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class Unregister extends Action<String> {

    private long signature;

    public Unregister(long signature) {
        this.signature = signature;
    }


    @Override
    protected void start() {
        System.out.println("Unregister");
        String studentID = String.valueOf(signature);

        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

        for (String s : privateState.getRegStudents()) {
            if (s.equals(studentID)) {
                privateState.getRegStudents().remove(s);
                privateState.setAvailableSpots(privateState.getAvailableSpots() + 1);
                privateState.setRegistered(privateState.getRegistered() - 1);

                break;
            }
        }

        StudentPrivateState studentPrivateState = (StudentPrivateState) getPool().getPrivateStateMap().get(studentID);
        studentPrivateState.getGrades().remove(getActorId());


    }
}
