package helldivers.test;

import helldivers.HelldiversAPIWrapper;
import util.Logging;

import java.util.Map;

public class TestHelldiversAPIWrapper {
    public static void main(String[] args){
        final String TAG = "TestHelldiversAPIWrapper";
        Map returnValue = (Map) HelldiversAPIWrapper.doHTTPRequest("get_status");
        Logging.log(returnValue.toString(), TAG);
    }
}
