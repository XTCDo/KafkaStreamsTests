package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Logtest {
    private static final String TAG = "logtest";

    public static void main(String[] args){


        // depth test
        Level[] levelArray= new Level[]{Level.INFO, Level.WARNING, Level.SEVERE, Level.FINE, Level.FINER, Level.FINEST};



        List<Logging> logList = new ArrayList<>();
        logList.add(new Logging());
        logList.add(new Logging(true, false));
        logList.add(new Logging(false, true));

        logList.forEach(logging -> {
            Arrays.stream(levelArray).forEach(level -> logging.log(level, TAG, "testing "+level.getName()));
        });

        // stress test
        for (int i=0; i<100000; i++){
            logList.get(0).debug(TAG,"stress test iteration"+ i);
        }

        Logging.logprint("console","just printing to console");

    }
}
