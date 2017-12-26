package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.subActions.SubOpenNewCourse;

import java.util.ArrayList;

public class OpenNewCourse extends Action<String> {

    private String courseName;
    private Integer numOfSpaces;
    private ArrayList<String> prerequisites;

    public OpenNewCourse(String courseName, Integer numOfSpaces, ArrayList<String> prequisites) {
        setActionName("Open Course");
        this.courseName = courseName;
        this.numOfSpaces = numOfSpaces;
        this.prerequisites = prequisites;

    }


    @Override
    protected void start() {

        SubOpenNewCourse subOpenNewCourse = new SubOpenNewCourse(this.numOfSpaces, this.prerequisites);
        ArrayList<Action<?>> actions = new ArrayList<>();
        actions.add(subOpenNewCourse);

        sendMessage(subOpenNewCourse, courseName, new CoursePrivateState());

        then(actions, () -> {

            if (getPrivateState() instanceof DepartmentPrivateState) {
                ((DepartmentPrivateState) (getPrivateState())).addCourse(courseName);
            }

            //complete
            CoursePrivateState sub = ((CoursePrivateState) (subOpenNewCourse.getPrivateState()));
            complete("OpenNewCourse sucess: \tOpened course: " + courseName + " (" + sub.getAvailableSpots() + ")");

            System.out.println(getResult().get());

        });

    }


}
