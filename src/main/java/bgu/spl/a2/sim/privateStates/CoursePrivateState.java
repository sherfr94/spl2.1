package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.ArrayList;
import java.util.List;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState {

    private Integer availableSpots;
    private Integer registered;
    private List<String> regStudents;
    private List<String> prequisites;

    /**
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     */
    public CoursePrivateState() {
        availableSpots = 0;
        registered = 0;
        regStudents = new ArrayList<>();
        prequisites = new ArrayList<>();
    }

    /**
     * @return available spots
     */
    public Integer getAvailableSpots() {
        return availableSpots;
    }

    /**
     * set available spots
     * @param availableSpots
     */
    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }

    /**
     *
     * @return num of registered students
     */
    public Integer getRegistered() {
        return registered;
    }

    /**
     * set num of registered students
     * @param registered
     */
    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    /**
     *
     * @return registered students
     */
    public List<String> getRegStudents() {
        return regStudents;
    }

    /**
     * set registered students
     * @param regStudents
     */
    public void setRegStudents(List<String> regStudents) {
        this.regStudents = regStudents;
    }

    /**
     *
     * @return prerequisites list
     */
    public List<String> getPrequisites() {
        return prequisites;
    }

    /**
     * set prerequisites list
     * @param prequisites
     */
    public void setPrequisites(List<String> prequisites) {
        this.prequisites = prequisites;
    }
}
