package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

public class Computer {

    String computerType;
    long failSig;
    long successSig;

    public void setComputerType(String computerType) {
        this.computerType = computerType;
    }

    public long getFailSig() {
        return failSig;
    }

    public void setFailSig(long failSig) {
        this.failSig = failSig;
    }

    public long getSuccessSig() {
        return successSig;
    }

    public void setSuccessSig(long successSig) {
        this.successSig = successSig;
    }

    public Computer(String computerType) {
        this.computerType = computerType;
    }

    /**
     * this method checks if the courses' grades fulfill the conditions
     *
     * @param courses       courses that should be pass
     * @param coursesGrades courses' grade
     * @return a signature if couersesGrades grades meet the conditions
     */
    public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades) {
        for (String name : courses) {//TODO is >=56 or just >56 to pass
            if (coursesGrades.get(name) == null) return failSig;
            if (coursesGrades.get(name) < 56) return failSig;//TODO: BUG nullpointer
        }
        return successSig;
    }

    public String getComputerType() {
        return computerType;
    }
}
