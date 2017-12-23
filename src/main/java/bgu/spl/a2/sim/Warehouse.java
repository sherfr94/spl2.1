package bgu.spl.a2.sim;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 * releasing and acquiring should be blocking free.
 */
public class Warehouse {
    //finite amount of computers
    private ArrayList<Computer> computerList;//TODO do we need to set MAX_SIZE of computer array

    //each compute has suspending mutex
    private HashMap<Computer, SuspendingMutex> computerSuspendingMutexMap;

    //mutexes
    private ArrayList<SuspendingMutex> suspendingMutexArray;//TODO forum


}
