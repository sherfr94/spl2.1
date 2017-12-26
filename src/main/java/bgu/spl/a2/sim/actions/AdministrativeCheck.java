package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;
import bgu.spl.a2.sim.subActions.SubAdministrativeCheck;

import java.util.ArrayList;

public class AdministrativeCheck extends Action<String> {

    private ArrayList<String> students;
    private ArrayList<String> courses;
    private SuspendingMutex mutex;

    public AdministrativeCheck(ArrayList<String> students, ArrayList<String> courses, SuspendingMutex mutex) {
        this.students = students;
        this.courses = courses;
        this.mutex = mutex;
    }

    @Override
    protected void start() {


        ArrayList<Action<?>> actions = new ArrayList<>();
        for (String s : students) {

            SubAdministrativeCheck subAdministrativeCheck = new SubAdministrativeCheck(courses, mutex);
            actions.add(subAdministrativeCheck);
            sendMessage(subAdministrativeCheck, s, new StudentPrivateState());
        }

        then(actions, () -> {
            System.out.println("##########4");
            complete("AdminCheck success:");
            System.out.println(getResult().get());
        });


        //params: students, computer, conditions - (namees of courses)

        //warehouse.computer.checkandsign to private state of students signature

        //
    }
}
