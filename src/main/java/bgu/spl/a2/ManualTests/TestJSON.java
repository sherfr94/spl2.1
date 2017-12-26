package bgu.spl.a2.ManualTests;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.ArrayList;

public class TestJSON {
    public static void main(String[] args) throws InterruptedException {

        ActorThreadPool pool = new ActorThreadPool(8);

        pool.start();

        OpenNewCourse openNewCourse = new OpenNewCourse("Intro to CS", 0, null);
        OpenNewCourse openNewCourse1 = new OpenNewCourse("SPL", 0, null);
        AddStudent addStudent = new AddStudent("1");
        AddStudent addStudent1 = new AddStudent("2");
        AddStudent addStudent2 = new AddStudent("3");
        ParticipatingInCourse participatingInCourse = new ParticipatingInCourse("1", "100");
        ParticipatingInCourse participatingInCourse1 = new ParticipatingInCourse("2", "100");
        CloseACourse closeACourse = new CloseACourse("Intro to CS");
        //ParticipatingInCourse participatingInCourse2 = new ParticipatingInCourse("2", "100");
        ArrayList<String> courses = new ArrayList<>();
        courses.add("SPL");
        courses.add("Intro to CS");
        ArrayList<String> grades = new ArrayList<>();
        grades.add("100");
        grades.add("100");
        RegisterWithPreferences registerWithPreferences = new RegisterWithPreferences(courses, grades);




        pool.submit(openNewCourse, "CS Department", new DepartmentPrivateState());
        pool.submit(openNewCourse1, "CS Department", new DepartmentPrivateState());
        pool.submit(addStudent, "CS Department", new DepartmentPrivateState());
        pool.submit(addStudent1, "CS Department", new DepartmentPrivateState());
        pool.submit(addStudent2, "CS Department", new DepartmentPrivateState());
        Thread.sleep(1000);

        pool.submit(participatingInCourse, "Intro to CS", new CoursePrivateState());
        pool.submit(participatingInCourse1, "Intro to CS", new CoursePrivateState());
        pool.submit(registerWithPreferences, "3", new StudentPrivateState());
        Thread.sleep(1000);
        //pool.submit(participatingInCourse2, "SPL", new CoursePrivateState());
        pool.submit(closeACourse, "CS", new DepartmentPrivateState());



        pool.shutdown();

        Thread.sleep(300);

        System.out.println(((CoursePrivateState) (pool.getPrivateStateMap().get("Intro to CS"))).getAvailableSpots());
        System.out.println(((CoursePrivateState) (pool.getPrivateStateMap().get("SPL"))).getAvailableSpots());
        System.out.println(((StudentPrivateState) (pool.getPrivateStateMap().get("1"))).getGrades().get("Intro To CS"));


    }
}
