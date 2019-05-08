package kafka.test;

import kafka.consumers.VaillantConsumer;
import util.Logging;

public class TestVaillantConsumer {
    private static final String TAG = "TestVaillantConsumer";

    public static void main(String[] args) {
        Logging.log("Starting VaillantConsumer test", TAG);
        VaillantConsumer vc = new VaillantConsumer();
        vc.run();
    }
}
