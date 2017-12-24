package bgu.spl.a2.ManualTests;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.AddStudent;
import bgu.spl.a2.sim.actions.OpenNewCourse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

import java.util.Map;

public class UniActionTests {
    public static void main(String[] args) throws InterruptedException {
        ActorThreadPool pool = new ActorThreadPool(8);
        Action<String> openNewCourse1 = new OpenNewCourse("SPL", 300, null);
        Action<String> openNewCourse2 = new OpenNewCourse("Data Bases", 200, null);
        Action<String> addStudent1 = new AddStudent("123456789");

        pool.start();

        pool.submit(openNewCourse1, "CS", new DepartmentPrivateState());
        pool.submit(openNewCourse2, "CS", new DepartmentPrivateState());
        pool.submit(addStudent1, "CS", new DepartmentPrivateState());//TODO always new DepartmentPrivateState???

        Thread.currentThread().sleep(300);

        for (Map.Entry<String, PrivateState> entry : pool.getPrivateStateMap().entrySet()) {
            System.out.println(entry.getKey());
        }


        pool.shutdown();
    }
}
