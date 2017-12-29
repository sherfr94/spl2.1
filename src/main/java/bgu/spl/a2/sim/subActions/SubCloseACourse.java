package bgu.spl.a2.sim.subActions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.actions.Unregister;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

import java.util.ArrayList;

public class SubCloseACourse extends Action<Boolean> {

    /**
     * action
     */
    @Override
    protected void start() {


        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

        if (privateState.getRegistered() != 0) {

            ArrayList<Action<?>> actions = new ArrayList<>();

            for (String s : privateState.getRegStudents()) {
                Unregister unregister = new Unregister(s);
                sendMessage(unregister, getActorId(), new CoursePrivateState());
                actions.add(unregister);

            }


            then(actions, () -> {
                privateState.setAvailableSpots(-1);
                complete(true);
            });


        } else {
            privateState.setAvailableSpots(-1);
            complete(true);
        }


    }
}
