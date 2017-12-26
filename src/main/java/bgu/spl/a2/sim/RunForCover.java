package bgu.spl.a2.sim;

import java.io.FileNotFoundException;

public class RunForCover {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            System.out.println("******************" + i);
            Simulator.main(new String[]{"myJson1.json"});
            Thread.sleep(250);
        }
    }
}
