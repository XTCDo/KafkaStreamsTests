package util.test;

import util.Logging;
import util.LoggingFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogging {
    public static void main(String[] args) {
        Map<Level, String> logThis = new HashMap<Level, String>();
        logThis.put(Level.SEVERE, "This is severe");
        logThis.put(Level.WARNING, "This is warning");
        logThis.put(Level.INFO, "This is info");
        logThis.put(Level.CONFIG, "This is config");
        logThis.put(Level.FINE, "This is fine");
        logThis.put(Level.FINER, "This is finer");
        logThis.put(Level.FINEST, "This is finest");


        logThis.forEach((key, value) -> {Logging.log(key, String.format("%s: %s", key.getName(), value));});
    }
}
