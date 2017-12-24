package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
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

        //check for space
        if (((CoursePrivateState) getPrivateState()).getAvailableSpots() == 0) {
            System.out.println(((CoursePrivateState) getPrivateState()).getAvailableSpots());
            complete("Can't register student: No space left");
            System.out.println(getResult().get());
            return;
        }

        //enough space
        else {
            CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

            SubParticipatingInCourse subParticipatingInCourse = new SubParticipatingInCourse(grade, getActorId(), privateState.getPrequisites());
            ArrayList<Action<?>> actions = new ArrayList<>();

            actions.add(subParticipatingInCourse);
            sendMessage(subParticipatingInCourse, studentID, new StudentPrivateState());

            then(actions, () -> {

                Boolean result = subParticipatingInCourse.getResult().get();
                //prerequisits ok
                if (result) {

                    privateState.getRegStudents().add(studentID);
                    privateState.setAvailableSpots(privateState.getAvailableSpots() - 1);
                    privateState.setRegistered(privateState.getRegistered() + 1);
                    complete("Student: " + studentID + ", participates in course: " + getActorId());
                    System.out.println(getResult().get());
                }
                //prerequisites not ok
                else {
                    complete("Can't register student: No Prerequisites");
                    System.out.println(getResult().get());
                }
            });

        }


    }
}
