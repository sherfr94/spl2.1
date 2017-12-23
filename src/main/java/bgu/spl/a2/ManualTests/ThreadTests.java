package bgu.spl.a2.ManualTests;

import java.util.ArrayList;

public class ThreadTests {
    public static void main(String[] args) {
        ArrayList<Thread> threadsArray = new ArrayList();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            threadsArray.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++) {

                        System.out.println("Thread #: " + finalI);
                    }
                }
            }));
        }


        for (Thread t : threadsArray) {
            t.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadsArray.get(0).getState());
        //notifyAll();


    }
}
