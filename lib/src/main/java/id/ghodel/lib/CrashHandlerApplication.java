package id.ghodel.lib;

import android.app.Application;

public class CrashHandlerApplication extends Application {

    private static CrashHandlerApplication singleton = null;

    public static CrashHandlerApplication getInstance(){
        if(singleton == null ){
            singleton = new CrashHandlerApplication();
        }
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        CrashHandler.init(singleton);
    }
}
