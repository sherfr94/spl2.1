package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

public class OpeningNewPlaceInACourse extends Action<String> {

    private Integer space;

    /**
     * Constructor
     *
     * @param space
     */
    public OpeningNewPlaceInACourse(Integer space) {
        this.space = space;
    }

    /**
     * action
     */
    @Override
    protected void start() {

        CoursePrivateState privateState = ((CoursePrivateState) getPrivateState());

        if (privateState.getAvailableSpots() == -1) {
            complete("AddSpaces fail: \t Can't add spaces to a closed course");
        } else {
            privateState.setAvailableSpots(privateState.getAvailableSpots() + space);
            complete("AddSpaces success: \tAdded spaces to: " + getActorId() + " (+" + space + ")");
            //System.out.println(getResult().get());
        }


    }
}
