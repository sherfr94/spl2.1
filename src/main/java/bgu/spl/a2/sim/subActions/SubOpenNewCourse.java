package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.ArrayList;

public class SubOpenNewCourse extends Action<String> {

    private Integer numOfSpaces;
    private ArrayList<String> prerequisites;

    /**
     * Constructor
     *
     * @param numOfSpaces
     * @param prerequisites
     */
    public SubOpenNewCourse(Integer numOfSpaces, ArrayList<String> prerequisites) {
        this.numOfSpaces = numOfSpaces;
        this.prerequisites = prerequisites;
    }

    /**
     * action
     */
    @Override
    protected void start() {
        ((CoursePrivateState) getPrivateState()).setAvailableSpots(numOfSpaces);
        ((CoursePrivateState) getPrivateState()).setPrequisites(prerequisites);

        complete("Course: " + getActorId() + " opened successfully");

    }
}
