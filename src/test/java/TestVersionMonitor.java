import bgu.spl.a2.VersionMonitor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestVersionMonitor {

    @Test
    public void testGetVersion() {

        try {//Case getVersion() without exceptions
            VersionMonitor vm = new VersionMonitor();
            int x = vm.getVersion();
            assertEquals(x, 0);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testInc() {

        try {//Case inc() getVersion() should be +1
            VersionMonitor vm = new VersionMonitor();
            int x1 = vm.getVersion();
            vm.inc();
            int x2 = vm.getVersion();
            assertEquals(x1 + 1, x2);
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testAwait() {

        try {//Check for InterruptedException and Thread.State

            VersionMonitor vm = new VersionMonitor();
            int ver = vm.getVersion();

            Thread t1 = new Thread(() -> {
                try {
                    vm.await(ver);
                } catch (InterruptedException ex) {
                    //PASS
                } catch (Exception ex) {
                    fail();
                }
            });

            t1.start();

            try {
                assertEquals(t1.getState().toString(), "RUNNABLE");
                vm.inc();
                Thread.sleep(100);
                assertEquals(t1.getState().toString(), "TERMINATED");
            } catch (Exception ex) {
                fail();
            }
        } catch (Exception ex) {
            fail();
        }
    }
}