import bgu.spl.a2.Promise;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestPromise {

    @Test
    public void testGet() {
        try { //Check p.get when not resolved
            Promise<Integer> p = new Promise<>();
            try {
                p.get();
            } catch (IllegalStateException ex) {
                //PASS
            } catch (Exception ex) {
                fail();
            }
        } catch (Exception ex) {
            fail();
        }

        try { //Check p.get when resolved
            Promise<Integer> p = new Promise<>();
            p.resolve(5);
            try {
                Integer x = p.get();
                assertEquals(x, new Integer(5));
            } catch (Exception ex) {
                fail();
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testIsResolved() {
        try { // Case not resolved
            Promise<Integer> p = new Promise<>();
            boolean result = p.isResolved();
            if (result) fail();
        } catch (Exception ex) {
            fail();
        }

        try { // Case resolved
            Promise<Integer> p = new Promise<>();
            p.resolve(5);
            boolean result = p.isResolved();
            if (!result) fail();
        } catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void testResolve() {
        try {
            Promise<Integer> p = new Promise<>();
            p.resolve(5);
            try {
                p.resolve(6);
                fail();
            } catch (IllegalStateException ex) {
                Integer x = p.get();
                assertEquals(x, new Integer(5));
            } catch (Exception ex) {
                fail();
            }
        } catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testSubscribe() {
        try {
            AtomicInteger x = new AtomicInteger(0);
            Promise<Integer> p = new Promise<>();
            p.resolve(5);
            p.subscribe(() -> x.set(1));
            assertEquals(x.get(), 1);
        } catch (Exception ex) {
            fail();
        }
    }

}