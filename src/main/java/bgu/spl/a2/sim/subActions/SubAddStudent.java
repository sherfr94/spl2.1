package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;

public class SubAddStudent extends Action<String> {

    private String studentID;

    public SubAddStudent(String studentID) {
        this.studentID = studentID;
    }

    @Override
    protected void start() {

        complete("Student " + studentID + " added successfully");
    }
}
