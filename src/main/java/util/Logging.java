package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


public class Logging {

    // === public functs ===

    /**
     *
     * @param tag
     * @param message
     */
    public static void logprint(String tag, String message){
        System.out.println(fullmessage(tag,message));
    }

    public static void log(){

    }

    // === private functions ===

    /**
     * converts circumstantial parameters to a timestamed, printable message
     * @param tag
     * @param message
     * @return
     */
    private static String fullmessage(String tag, String message){
        return new StringBuilder()
                .append("<").append(now()).append("> ")
                .append("[").append(tag).append("]: ")
                .append(message)
                .toString();
    }

    private static String now(){
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}

import logging
        from time import strftime, localtime


        # logging function that provides timestamps and tags for debugging
        def log(level, tag, message):
        try:
        tag = str(tag)
        message = str(message)
        time_stamp = strftime("%H:%M:%S", localtime())
        # levels are defined in library API
        level = str(level).lower()
        if level in ["c", "crit", "critical"]:
        level = 50
        elif level in ["e", "err", "error"]:
        level = 40
        elif level in ["w", "warn", "warning"]:
        level = 30
        elif level in ["i", "info"]:
        level = 20
        elif level in ["d", "debug"]:
        level = 10
        else:
        level = 0
        logging.log(level, "<%s> [%s]: %s" % (time_stamp, tag, message))
        # when exceptions happen, don't hold on to them but throw them in the caller's face
        # so they can properly handle them
        except Exception as e:
        raise Exception(e)
