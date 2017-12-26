/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.*;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import json.ActionConfig;
import json.ComputerConfig;
import json.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

    private static Config config;
    private static CountDownLatch latch;
    //private static int i = 1;
    private static Object lock;

    public static void setConfig(Config config) {
        Simulator.config = config;
    }

    public static ActorThreadPool actorThreadPool;

    /**
     * Begin the simulation Should not be called before attachActorThreadPool()
     */
    public static void start() {

        Warehouse warehouse = Warehouse.getInstance();
        ArrayList<ComputerConfig> computers = config.getComputers();
        for (ComputerConfig cc : computers) {
            Computer computer = new Computer(cc.getType());
            computer.setFailSig(cc.getFailSig());
            computer.setSuccessSig(cc.getSuccessSig());
            Warehouse.addCmputer(computer);
        }

        System.out.println("=========");
        System.out.println("PHASE1");
        System.out.println("=========");

        //Phase1
        ArrayList<ActionConfig> phase1Actions = config.getPhase1();
        actorThreadPool.start();


        latch = new CountDownLatch(phase1Actions.size());

        for (ActionConfig ac : phase1Actions) {
            doAction(ac);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        if (latch.getCount() == 0) {

            System.out.println("=========");
            System.out.println("PHASE2");
            System.out.println("=========");
        }


        //Phase2
        ArrayList<ActionConfig> phase2Actions = config.getPhase2();
        latch = new CountDownLatch(phase2Actions.size());

        for (ActionConfig ac : phase2Actions) {
            doAction(ac);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (latch.getCount() == 0) {

            System.out.println("=========");
            System.out.println("PHASE3");
            System.out.println("=========");
        }

        //Phase3
        ArrayList<ActionConfig> phase3Actions = config.getPhase3();

        for (ActionConfig ac : phase3Actions) {
            doAction(ac);
        }

    }

    /**
     * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
     *
     * @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
     */
    public static void attachActorThreadPool(ActorThreadPool myActorThreadPool) {
        actorThreadPool = myActorThreadPool;
    }

    /**
     * shut down the simulation
     * returns list of private states
     */
    public static HashMap<String, PrivateState> end() {
        try {
            actorThreadPool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return actorThreadPool.getPrivateStateMap();
    }


    public static void doAction(ActionConfig ac) {

        Action<String> action = null;
        String actionName = ac.getAction();

        if (actionName.equals("Open Course")) {
            action = new OpenNewCourse(ac.getCourse(), ac.getSpace(), ac.getPrerequisites());
            action.getResult().subscribe(() -> {
                latch.countDown();

            });
            actorThreadPool.submit(action, ac.getDepartment(), new DepartmentPrivateState());
            actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
        } else if (actionName.equals("Add Student")) {
            action = new AddStudent(ac.getStudent());
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getDepartment(), new DepartmentPrivateState());
            actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
        } else if (actionName.equals("Participate In Course")) {
            action = new ParticipatingInCourse(ac.getStudent(), ac.getGrade().get(0));
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getCourse(), new CoursePrivateState());
            actorThreadPool.getPrivateState(ac.getCourse()).addRecord(actionName);
        } else if (actionName.equals("Unregister")) {
            action = new Unregister(ac.getStudent());
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getCourse(), new CoursePrivateState());
            actorThreadPool.getPrivateState(ac.getCourse()).addRecord(actionName);
        } else if (actionName.equals("Close Course")) {//TODO how to write close a course
            action = new CloseACourse(ac.getCourse());
            actorThreadPool.submit(action, ac.getDepartment(), new DepartmentPrivateState());
            actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
        } else if (actionName.equals("Add Spaces")) {
            action = new OpeningNewPlaceInACourse(ac.getNumber());
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getCourse(), new CoursePrivateState());
            actorThreadPool.getPrivateState(ac.getCourse()).addRecord(actionName);
        } else if (actionName.equals("Administrative Check")) {
            action = new AdministrativeCheck(ac.getStudents(), ac.getConditions(), Warehouse.getMutex(ac.getComputer()));
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getDepartment(), new DepartmentPrivateState());
            actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
        } else if (actionName.equals("Register With Preferences")) {//TODO complete params
            action = new RegisterWithPreferences(ac.getPreferences(), ac.getGrade());
            action.getResult().subscribe(() -> {
                latch.countDown();
            });
            actorThreadPool.submit(action, ac.getStudent(), new StudentPrivateState());
            actorThreadPool.getPrivateState(ac.getStudent()).addRecord(actionName);
        }


//        if (action != null) {
//            Promise<String> promise = action.getResult();
//            promise.subscribe(() -> {
//                System.out.println("Latch: " + latch.getCount()+" "+actionName);
//                latch.countDown();
//            });
//        }


    }


    public static int main(String[] args) throws FileNotFoundException {

        String fileName = args[0];

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(fileName));

        Config config = gson.fromJson(reader, Config.class);
        Simulator.setConfig(config);

        ActorThreadPool pool = new ActorThreadPool(config.getThreads());
        Simulator.attachActorThreadPool(pool);

        start();

        HashMap<String, PrivateState> simResult;
        simResult = end();
        FileOutputStream fout = new FileOutputStream("result.ser");
        ObjectOutputStream oos = null;


        try {
            oos = new ObjectOutputStream(fout);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            oos.writeObject(simResult);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO do not forget warehouse and computer
        return 1;

    }
}
