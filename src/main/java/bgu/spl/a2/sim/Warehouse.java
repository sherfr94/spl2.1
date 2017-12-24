package bgu.spl.a2.sim;

import java.util.HashMap;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {

    private static HashMap<String, SuspendingMutex> warehouse;

    private static Warehouse instance = null;

    private Warehouse() {
        warehouse = new HashMap<>();
    }

    public static Warehouse getInstance() {
        if (instance == null) {

            instance = new Warehouse();
        }
        return instance;
    }

    public static void addCmputer(Computer computer) {
        SuspendingMutex suspendingMutex = new SuspendingMutex(computer);
        warehouse.put(computer.getComputerType(), suspendingMutex);
    }

    public static SuspendingMutex getMutex(String computerType) {
        return warehouse.get(computerType);
    }




}
