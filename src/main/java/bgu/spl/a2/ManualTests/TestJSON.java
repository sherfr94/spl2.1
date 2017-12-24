package bgu.spl.a2.ManualTests;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.sim.actions.AddStudent;
import bgu.spl.a2.sim.actions.OpenNewCourse;
import bgu.spl.a2.sim.actions.ParticipatingInCourse;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

public class TestJSON {
    public static void main(String[] args) throws InterruptedException {

        ActorThreadPool pool = new ActorThreadPool(8);

        OpenNewCourse openNewCourse = new OpenNewCourse("Intro to CS", 30, null);
        AddStudent addStudent = new AddStudent("123456789");
        ParticipatingInCourse participatingInCourse = new ParticipatingInCourse("123456789", "100");
        pool.start();


        pool.submit(openNewCourse, "CS Department", new DepartmentPrivateState());
        pool.submit(addStudent, "CS Department", new DepartmentPrivateState());

        pool.submit(participatingInCourse, "Intro to CS", new CoursePrivateState());


        pool.shutdown();

        Thread.sleep(300);

        System.out.println(((DepartmentPrivateState) (pool.getPrivateStateMap().get("CS Department"))).getCourseList().get(0));
        System.out.println(((DepartmentPrivateState) (pool.getPrivateStateMap().get("CS Department"))).getStudentList().get(0));
        System.out.println(((CoursePrivateState) (pool.getPrivateState("Intro to CS"))).getAvailableSpots());


    }
}
