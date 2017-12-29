package json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComputerConfig {
    /**
     * This class represents deserializing json to computer
     */

    @SerializedName("Type")
    @Expose
    private String type;

    @SerializedName("Sig Success")
    @Expose
    private Long successSig;

    @SerializedName("Sig Fail")
    @Expose
    private Long failSig;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSuccessSig() {
        return successSig;
    }

    public void setSuccessSig(Long successSig) {
        this.successSig = successSig;
    }

    public Long getFailSig() {
        return failSig;
    }

    public void setFailSig(Long failSig) {
        this.failSig = failSig;
    }
}
