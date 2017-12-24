package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlaceInACourse extends Action<String> {

    private Integer space;

    public OpeningNewPlaceInACourse(Integer space) {
        this.space = space;
    }

    @Override
    protected void start() {

        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

        privateState.setAvailableSpots(privateState.getAvailableSpots() + space);
        complete("Add Spaces succeeded: " + getActorId() + " (+" + space + ")");
        System.out.println(getResult().get());
    }
}
