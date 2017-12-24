package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import bgu.spl.a2.sim.subActions.SubParticipatingInCourse;

import java.util.ArrayList;

public class ParticipatingInCourse extends Action<String> {

    private String studentID;
    private String grade;

    public ParticipatingInCourse(String studentID, String grade) {
        this.studentID = studentID;
        this.grade = grade;
    }

    @Override
    protected void start() {

        if (((CoursePrivateState) getPrivateState()).getAvailableSpots() == 0) {
            complete("Participate failed: No space left");
            System.out.println(getResult().get());
        }


        //enough space
        else {
            CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

            SubParticipatingInCourse subParticipatingInCourse = new SubParticipatingInCourse(grade, getActorId(), privateState.getPrequisites());
            ArrayList<Action<?>> actions = new ArrayList<>();

            actions.add(subParticipatingInCourse);
            getPool().submit(subParticipatingInCourse, studentID, new StudentPrivateState());
            Promise<Boolean> promise = subParticipatingInCourse.getResult();

            promise.subscribe(() -> {
                Boolean result = subParticipatingInCourse.getResult().get();
                //prerequisits ok
                if (result) {//TODO: BUG: nullPointerExeption

                    privateState.getRegStudents().add(studentID);
                    privateState.setAvailableSpots(privateState.getAvailableSpots() - 1);
                    privateState.setRegistered(privateState.getRegistered() + 1);
                    complete("Participate succeed: Student: " + studentID + " registered to course: " + getActorId());
                    System.out.println(getResult().get());
                }
                //prerequisites not ok
                else {
                    complete("Participate failed: No prerequisites");
                    System.out.println(getResult().get());
                }
            });


        }
    }


}

