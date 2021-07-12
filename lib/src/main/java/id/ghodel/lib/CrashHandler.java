package id.ghodel.lib;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import id.ghodel.lib.Activity.CrashActivity;
import id.ghodel.lib.utils.CrashUtil;

import static id.ghodel.lib.utils.CrashUtil.writeFile;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private static CrashHandler mCrashHandler  = new CrashHandler();
    private Context mContext;

    private String Email;
    private String backgroundColor;
    private boolean isAllowToSave;

    //variable dump device info
    private String date = "";
    private String manufacturer = "";
    private String model = "";
    private String version = "";
    private String SDKInfo = "";

    //variable dump apk info
    private String packageName = "";
    private String appVersionName = "";
    private String appVersionCode = "";

    public CrashHandler(){

    }

    public static CrashHandler getInstance(){
        return mCrashHandler;
    }

    public String getEmail() {
        return Email;
    }

    public CrashHandler setEmail(String email) {
        Email = email;
        return this;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public CrashHandler setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public boolean isAllowToSave() {
        return isAllowToSave;
    }

    public CrashHandler saveErrorToPath(boolean allowToSave) {
        isAllowToSave = allowToSave;
        return this;
    }

    public  CrashHandler init(Application app){
        mContext = app.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(this);
        return this;
    }


    @Override
    public void uncaughtException(Thread t , Throwable e) {
        try {
            tryUncaughtException(t, e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void tryUncaughtException(Thread thread, Throwable throwable) {
        final String time = new SimpleDateFormat("MM-dd-yyyy_HH:mm:ss").format(new Date());
        File crashFile = new File(mContext.getExternalFilesDir(null), "crash_" + time + ".txt");

        long versionCode = 0;
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            packageName = mContext.getPackageName();
            appVersionName = packageInfo.versionName;
            versionCode = Build.VERSION.SDK_INT >= 28 ? packageInfo.getLongVersionCode()
                    : packageInfo.versionCode;
            appVersionCode = String.valueOf(versionCode);
        } catch (PackageManager.NameNotFoundException ignored) {

        }

        String fullStackTrace; {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            fullStackTrace = sw.toString();
            pw.close();
        }

        date = time;
        manufacturer = Build.MANUFACTURER;
        model = Build.MODEL;
        version = Build.VERSION.RELEASE;
        SDKInfo = String.valueOf(Build.VERSION.SDK_INT);


        StringBuilder sb = new StringBuilder();
        sb.append("*** Device Information ***").append("\n\n");
        sb.append("Time Of Crash             : ").append(time).append("\n");
        sb.append("Device Manufacturer: ").append(Build.MANUFACTURER).append("\n");
        sb.append("Device Model              : ").append(Build.MODEL).append("\n");
        sb.append("Android Version          : ").append(Build.VERSION.RELEASE).append("\n");
        sb.append("Android SDK                 : ").append(Build.VERSION.SDK_INT).append("\n");
        sb.append("App VersionName     : ").append(appVersionName).append("\n");
        sb.append("App VersionCode       : ").append(versionCode).append("\n\n");
        sb.append("*** Crash Head ***").append("\n");
        sb.append("\n").append(fullStackTrace);
        sb.append("\n\n").append("*** End of current Report ***");

        String errorLog = sb.toString();

        if(isAllowToSave){
            try {
                writeFile(crashFile, errorLog);
            } catch (IOException ignored) {

            }
        }


        Intent intent = null;
        intent = new Intent(mContext, CrashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        intent.putExtra(CrashActivity.DATE, ": "+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        intent.putExtra(CrashActivity.MANUFACTURER, ": "+manufacturer);
        intent.putExtra(CrashActivity.MODEL, ": "+model);
        intent.putExtra(CrashActivity.VERSION, ": "+version);
        intent.putExtra(CrashActivity.SDK, ": "+SDKInfo);
        intent.putExtra(CrashActivity.CRASHINFO, fullStackTrace);
        intent.putExtra(CrashActivity.CRASHCAUSE, CrashUtil.BASE_URL_STACKOVERFLOW + throwable.getCause().getLocalizedMessage());
        intent.putExtra(CrashActivity.EMAIL, getEmail());

        intent.putExtra(CrashActivity.BACKGROUND, getBackgroundColor());
        intent.putExtra(CrashActivity.PACKAGENAME, ": "+packageName);
        intent.putExtra(CrashActivity.APPVERSIONNAME, ": "+ appVersionName);
        intent.putExtra(CrashActivity.APPVERSIONCODE, ": "+ versionCode);

        try {
            mContext.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

}