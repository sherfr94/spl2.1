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

    /**
     * Constructor
     */
    private Warehouse() {
        warehouse = new HashMap<>();
    }

    /**
     * @return instance of warehouse
     */
    public static Warehouse getInstance() {
        if (instance == null) {

            instance = new Warehouse();
        }
        return instance;
    }

    /**
     * adds computer to warehouse map
     * @param computer
     */
    public static void addComputer(Computer computer) {
        SuspendingMutex suspendingMutex = new SuspendingMutex(computer);
        warehouse.put(computer.getComputerType(), suspendingMutex);
    }

    /**
     *
     * @param computerType
     * @return mutex for computer type
     */
    public static SuspendingMutex getMutex(String computerType) {
        return warehouse.get(computerType);
    }




}
