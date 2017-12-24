package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import bgu.spl.a2.sim.subActions.SubAddStudent;

import java.util.ArrayList;

public class AddStudent extends Action<String> {


    private String studentID;

    public AddStudent(String studentID) {
        this.studentID = studentID;
    }

    @Override
    protected void start() {


        SubAddStudent subAddStudent = new SubAddStudent(studentID);
        ArrayList<Action<?>> actions = new ArrayList<>();

        actions.add(subAddStudent);
        sendMessage(subAddStudent, studentID, new StudentPrivateState());

        then(actions, () -> {

            if (getPrivateState() instanceof DepartmentPrivateState) {
                ((DepartmentPrivateState) getPrivateState()).addStudent(studentID);
            }

            complete("Added student: " + studentID + " ");
            System.out.println(getResult().get());

        });


    }
}
