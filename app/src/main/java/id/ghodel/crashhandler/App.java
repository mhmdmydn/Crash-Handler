package id.ghodel.crashhandler;

import android.app.Application;

import id.ghodel.lib.CrashHandler;

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
        CrashHandler.getInstance()
                .init(singleton)
                .setEmail("ghodelchibar@gmail.com")
                .saveErrorToPath(true);
    }
}
