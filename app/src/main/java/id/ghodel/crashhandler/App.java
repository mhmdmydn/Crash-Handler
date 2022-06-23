package id.ghodel.crashhandler;

import android.app.Application;

import id.ghodel.crash.CrashHandler;
import id.ghodel.crash.data.DisplayType;

public class App extends Application {

    private static App singleton = null;

    public static App getInstance(){
        if(singleton == null ){
            singleton = new App();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        new CrashHandler.Builder(this)
                .setEmail("ghodelchibar@gmail.com")
                .setBuildType(BuildConfig.BUILD_TYPE)
                .saveCrashToFile(true)
                .showAs(DisplayType.DIALOG)
                .build();
    }
}
