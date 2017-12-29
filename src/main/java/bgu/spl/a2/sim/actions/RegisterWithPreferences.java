package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;

public class RegisterWithPreferences extends Action<String> {

    private ArrayList<String> courses;
    private ArrayList<String> grades;
    private int i;

    /**
     * Constructor
     *
     * @param courses
     * @param grades
     */
    public RegisterWithPreferences(ArrayList<String> courses, ArrayList<String> grades) {
        this.courses = courses;
        this.grades = grades;
        if (courses.isEmpty()) i = 0;
        else i = courses.size();
    }

    /**
     * action
     */
    @Override
    protected void start() {
        if (i == 0) {
            complete("Preferences fail: \tStudent: " + getActorId());
            //System.out.println(getResult().get());
        } else {
            StudentPrivateState privateState = ((StudentPrivateState) getPrivateState());
            ParticipatingInCourse participating = new ParticipatingInCourse(getActorId(), grades.get(0));
            String course = courses.get(0);
            courses.remove(0);
            grades.remove(0);
            ArrayList<Action<?>> actions = new ArrayList<>();
            actions.add(participating);
            sendMessage(participating, course, new CoursePrivateState());

            then(actions, () -> {
                if (participating.getResult().get().contains("success")) {
                    complete("Preferences success: \tStudent: " + getActorId());
                    //System.out.println(getResult().get());
                } else {

                    RegisterWithPreferences registerWithPreferences = new RegisterWithPreferences(courses, grades);
                    ArrayList<Action<?>> actions2 = new ArrayList<>();
                    actions2.add(registerWithPreferences);
                    sendMessage(registerWithPreferences, getActorId(), new StudentPrivateState());

                    then(actions2, () -> {
                        complete("");
                    });


                }
            });
        }

    }


}
