package util;

import java.util.logging.Level;

public class Logtest {
    private static final String TAG = "logtest";

    public static void main(String[] args){
        Logging log = new Logging();

        log.info(TAG,"starting up logtest");
        log.warn(TAG, "oh no, something's wrong!");
        log.log(Level.SEVERE,TAG,"Something has gone off the rails");

        Logging.logprint("console","just printing to console");
    }
}
