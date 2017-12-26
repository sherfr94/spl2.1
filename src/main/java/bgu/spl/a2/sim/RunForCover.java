package bgu.spl.a2.sim;

import java.io.FileNotFoundException;

public class RunForCover {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("******************" + i);
            Simulator.main(new String[]{"pdf.json"});
            Thread.sleep(250);
        }
    }
}
