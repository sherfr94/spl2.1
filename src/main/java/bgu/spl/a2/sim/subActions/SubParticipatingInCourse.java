package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.List;

public class SubParticipatingInCourse extends Action<Boolean> {

    private String grade;
    private String courseName;
    private List<String> prerequisites;

    public SubParticipatingInCourse(String grade, String courseName, List<String> prerequisites) {
        this.grade = grade;
        this.courseName = courseName;
        this.prerequisites = prerequisites;
    }

    @Override
    protected void start() {


        boolean problem = false;
        for (String course : prerequisites) {
            if ((((StudentPrivateState) getPrivateState()).getGrades()).get(course) == null) {
                problem = true;
                break;

            }
        }

        if (problem) {
            complete(false);
            return;
        } else {

            Integer gradeNum = new Integer(-1);

            //student state
            if (!grade.equals("-")) {
                gradeNum = Integer.parseInt(grade);
            }

            ((StudentPrivateState) getPrivateState()).addGrade(courseName, gradeNum);
            complete(true);

        }


    }
}
