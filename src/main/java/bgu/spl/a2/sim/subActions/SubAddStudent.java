package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;

public class SubAddStudent extends Action<String> {

    private String studentID;

    /**
     * constructor
     *
     * @param studentID
     */
    public SubAddStudent(String studentID) {
        this.studentID = studentID;
    }

    /**
     * action
     */
    @Override
    protected void start() {

        complete("Student " + studentID + " added successfully");
    }
}
