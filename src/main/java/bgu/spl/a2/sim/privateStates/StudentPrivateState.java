package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.HashMap;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState {

    private HashMap<String, Integer> grades;
    private long signature;

    /**
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     */
    public StudentPrivateState() {
        grades = new HashMap<>();

    }

    /**
     * add grade to map
     *
     * @param name
     * @param grade
     */
    public void addGrade(String name, Integer grade) {
        grades.put(name, grade);
    }

    /**
     *
     * @return get grades map
     */
    public HashMap<String, Integer> getGrades() {
        return grades;
    }

    /**
     *
     * @return signature value
     */
    public long getSignature() {
        return signature;
    }

    /**
     * set signature value
     * @param signature
     */
    public void setSignature(long signature) {
        this.signature = signature;
    }
}
