package util;

import java.util.logging.Level;

public class Logtest {
    private static final String TAG = "logtest";

    public static void main(String[] args){
        Logging log = new Logging();
        Logging log2 = new Logging();
        Logging log3 = new Logging();

        log.info(TAG,"starting up logtest");
        log.warn(TAG, "oh no, something's wrong!");
        log.log(Level.SEVERE,TAG,"Something has gone off the rails");

        log.log(Level.INFO, "testing tagless logging");

        Logging.logprint("console","just printing to console");
    }
}
