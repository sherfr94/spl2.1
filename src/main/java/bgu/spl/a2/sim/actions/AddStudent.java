package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class AddStudent extends Action<String> {


    private long signature;

    public AddStudent(long signature) {
        this.signature = signature;
    }

    @Override
    protected void start() {
        System.out.println("Starting AddStudent");
        String studentID = String.valueOf(signature);

        if (getPrivateState() instanceof DepartmentPrivateState) {
            (((DepartmentPrivateState) getPrivateState()).getStudentList()).add(studentID);
        }

        StudentPrivateState privateState = new StudentPrivateState();
        privateState.setSignature(signature);

        getPool().addActor(studentID, privateState);

        //complete
        complete("Added student: " + studentID + " ");
        System.out.println(getResult().get());


    }
}
