package ca.uwappdev.forum;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by daniel on 2015-10-14.
 */
public class ForumApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "dbSRgndFtVlkxJUG1AcubhVaP814yfO0HAqrEjaU", "LWqPDlBCoLfhG2bG7ZvjKdNpvcbl8JARkLHcaoWf");
    }
}
