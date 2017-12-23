package bgu.spl.a2.ManualTests;

import com.google.gson.Gson;

import java.util.Arrays;

public class TestJSON {
    public static void main(String[] args) {

        Gson gson = new Gson();

        String json = Arrays.toString(args);

        System.out.println(args[0]);


    }
}
