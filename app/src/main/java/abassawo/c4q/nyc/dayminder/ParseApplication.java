package abassawo.c4q.nyc.dayminder;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by c4q-Abass on 9/22/15.
 */
public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Required - Initialize the Parse SDK
        Parse.initialize(this, "TB8S38Z96VZQZzgYcZuFyWMBVFEmeky8YWzdKyuQ", "hcFFQLd9Ybtrw4unC7BiDgh38PTTwjORaOQY8Qbk");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);
    }
}
