/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.actions.AddStudent;
import bgu.spl.a2.sim.actions.OpenNewCourse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import json.ActionConfig;
import json.Config;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {

    private static Config config;

    public static void setConfig(Config config) {
        Simulator.config = config;
    }

    public static ActorThreadPool actorThreadPool;

    /**
     * Begin the simulation Should not be called before attachActorThreadPool()
     */
    public static void start() {

        ArrayList<ActionConfig> phase1Actions = config.getPhase1();

        for (ActionConfig ac : phase1Actions) {
            String actionName = ac.getAction();

            if (actionName.equals("Open Course")) {
                OpenNewCourse openNewCourse = new OpenNewCourse(ac.getCourse(), ac.getSpace(), ac.getPrerequisites());
                actorThreadPool.submit(openNewCourse, ac.getDepartment(), new DepartmentPrivateState());
                actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
            } else if (actionName.equals("Add Student")) {
                AddStudent addStudent = new AddStudent(ac.getStudent());
                actorThreadPool.submit(addStudent, ac.getDepartment(), new DepartmentPrivateState());
                actorThreadPool.getPrivateState(ac.getDepartment()).addRecord(actionName);
            }


        }

        actorThreadPool.start();
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


    public static int main(String[] args) throws FileNotFoundException {
        System.out.println("SIMULATOR RUNNING");
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
