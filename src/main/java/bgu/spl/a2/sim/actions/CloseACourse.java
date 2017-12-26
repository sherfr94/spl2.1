package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.subActions.SubCloseACourse;

import java.util.ArrayList;

public class CloseACourse extends Action<String> {

    private String courseName;

    public CloseACourse(String courseName) {
        this.courseName = courseName;
    }

    @Override
    protected void start() {

        DepartmentPrivateState privateState = ((DepartmentPrivateState) getPrivateState());
        ArrayList<Action<?>> actions = new ArrayList<>();

        SubCloseACourse subCloseACourse = new SubCloseACourse();
        actions.add(subCloseACourse);

        sendMessage(subCloseACourse, courseName, new CoursePrivateState());

        then(actions, () -> {
            privateState.getCourseList().remove(courseName);
            complete("Close Course success: \tClosed course: " + courseName);
            System.out.println(getResult().get());
        });



    }
}
