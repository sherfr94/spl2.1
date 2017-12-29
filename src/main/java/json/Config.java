package json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Config {
    /**
     * This class deserializes json to config
     */

    @SerializedName("threads")
    @Expose
    private Integer threads;

    @SerializedName("Computers")
    @Expose
    private ArrayList<ComputerConfig> computers;

    @SerializedName("Phase 1")
    @Expose
    private ArrayList<ActionConfig> phase1;

    @SerializedName("Phase 2")
    @Expose
    private ArrayList<ActionConfig> phase2;

    @SerializedName("Phase 3")
    @Expose
    private ArrayList<ActionConfig> phase3;


    public Integer getThreads() {
        return threads;
    }


    public ArrayList<ComputerConfig> getComputers() {
        return computers;
    }

    public void setComputers(ArrayList<ComputerConfig> computers) {
        this.computers = computers;
    }

    public ArrayList<ActionConfig> getPhase1() {
        return phase1;
    }

    public ArrayList<ActionConfig> getPhase2() {
        return phase2;
    }

    public ArrayList<ActionConfig> getPhase3() {
        return phase3;
    }
}
