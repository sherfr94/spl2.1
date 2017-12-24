package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;

public class Unregister extends Action<String> {

    private long signature;

    public Unregister(long signature) {
        this.signature = signature;
    }


    @Override
    protected void start() {



    }
}
