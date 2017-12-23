package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.ArrayList;

public class OpenNewCourse extends Action<String> {

    private String courseName;
    private Integer numOfSpaces;
    private ArrayList<String> prequisites;

    public OpenNewCourse(String courseName, Integer numOfSpaces, ArrayList<String> prequisites) {
        setActionName("Open Course");
        this.courseName = courseName;
        this.numOfSpaces = numOfSpaces;
        this.prequisites = prequisites;//TODO how to get list of prequisites and update

    }


    @Override
    protected void start() {
        System.out.println("Starting OpenNewCourse");
        //get private state, add name of course to list
        if (getPrivateState() instanceof DepartmentPrivateState) {
            ((DepartmentPrivateState) (getPrivateState())).getCourseList().add(courseName);
        }


        //create new private state of courses and

        CoursePrivateState privateState = new CoursePrivateState();
        privateState.setAvailableSpots(numOfSpaces);
        privateState.setPrequisites(prequisites);

        //add actor to pool

        getPool().addActor(courseName, privateState);

/*
        getPool().getActionsMap().put(courseName, new PriorityQueue<>());
        getPool().getIsTaken().put(courseName, new AtomicBoolean( false));
        getPool().getPrivateStateMap().put(courseName, privateState);
        */

        //complete
        complete("Course: " + courseName + " opened successfully");
        System.out.println(getResult().get());


    }


}
