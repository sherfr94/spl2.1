/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import json.Config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

/**
 * A class describing the simulator for part 2 of the assignment
 */
public class Simulator {


    public static ActorThreadPool actorThreadPool;

    /**
     * Begin the simulation Should not be called before attachActorThreadPool()
     */
    public static void start() {
        //parse json

        //submit actions to thread pool
    }

    /**
     * attach an ActorThreadPool to the Simulator, this ActorThreadPool will be used to run the simulation
     *
     * @param myActorThreadPool - the ActorThreadPool which will be used by the simulator
     */
    public static void attachActorThreadPool(ActorThreadPool myActorThreadPool) {
        //TODO: replace method body with real implementation
        throw new UnsupportedOperationException("Not Implemented Yet.");
    }

    /**
     * shut down the simulation
     * returns list of private states
     */
    public static HashMap<String, PrivateState> end() {
        //TODO: replace method body with real implementation
        throw new UnsupportedOperationException("Not Implemented Yet.");
    }


    public static int main(String[] args) throws FileNotFoundException {
        System.out.println("SIMULATOR RUNNING");

        String fileName = args[0];

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(fileName));
        Config config = gson.fromJson(reader, Config.class);

        System.out.println(config.getThreads());

        System.out.println(config.getComputers().get(0).getType());

        System.out.println(config.getPhase1().get(0).getAction());

        System.out.println(config.getPhase2().get(1).getAction());

        System.out.println(config.getPhase3().get(0).getStudent());


        return 1;

    }
}
