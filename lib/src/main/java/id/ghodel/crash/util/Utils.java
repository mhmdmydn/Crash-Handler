package id.ghodel.crash.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import id.ghodel.crash.data.model.CrashInfo;

/**
 * Created by Muhammad Mayudin on 12-Jul-21.
 */
public class Utils {

    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;

    public static final String BASE_URL_STACKOVERFLOW = "http://stackoverflow.com/search?q=[java][android]";

    public static void showToast(Context context, String msg, int time){
        switch (time){
            case LENGTH_SHORT:
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                break;
            case LENGTH_LONG:
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                break;
        }
    }

    public static void goToStackoverflow(Context context, String url){

        context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
        ((Activity)context).finish();
    }

    public static void goToEmailCrashBugReport(Context context, String email, String body){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "App Error");
        intent.putExtra(Intent.EXTRA_TEXT, body);

        context.startActivity(Intent.createChooser(intent, "Send Email To Developer"));
    }

    public static void shareError(Context context, String error){
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Error");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, error);
        /*Fire!*/
        context.startActivity(Intent.createChooser(intent, "Share To"));
    }

    public static void restartApp(Context context){
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(context.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
        ((Activity)context).finish();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static void writeFile(File file, String content) throws IOException {
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes());
        try {
            fos.close();
        } catch (IOException e) {

        }
    }

    public static void changeBackgroundColor(View view, String color){
        view.setBackgroundColor(Color.parseColor(color));
    }

    public static StackTraceElement parseThrowable(Context context, Throwable ex){
        if (ex == null || ex.getStackTrace() == null || ex.getStackTrace().length == 0) return null;
        StackTraceElement element;
        String packageName = context.getPackageName();
        for (StackTraceElement ele : ex.getStackTrace()) {
            if (ele.getClassName().contains(packageName)) {
                element = ele;
                return element;
            }
        }
        element = ex.getStackTrace()[0];
        return element;
    }

    public static String getVersionCode(Context context) {
        String versionCode = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = String.valueOf(packageInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = String.valueOf(packageInfo.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static StringBuilder readFile(String filePath, String charsetName) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(
                    file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    public static boolean writeFile(String filePath, String content,
                                    boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    private static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder
                .mkdirs();
    }

    private static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosition = filePath.lastIndexOf(File.separator);
        return (filePosition == -1) ? "" : filePath.substring(0, filePosition);
    }

    public static String getDensityString(DisplayMetrics displayMetrics) {
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi";
            case DisplayMetrics.DENSITY_TV:
                return "tvdpi";
            default:
                return String.valueOf(displayMetrics.densityDpi);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseCrashInfoToString(CrashInfo crashInfo){

        StringBuilder sb = new StringBuilder();
        sb.append("-= Build Information =-");
        sb.append("\nVersionName: ").append(crashInfo.getVersionName());
        sb.append("\nVersionCode: ").append(crashInfo.getVersionCode());
        sb.append("\nPackage: ").append(crashInfo.getPackageName());
        sb.append("\n\n");
        sb.append("-= Device Information =-");
        sb.append("\nManufacturer: ").append(crashInfo.getDeviceInfo().getManufacturer());
        sb.append("\nModel: ").append(crashInfo.getDeviceInfo().getModel());
        sb.append("\nBrand: ").append(crashInfo.getDeviceInfo().getBrand());
        sb.append("\nResolution: ").append(crashInfo.getDeviceInfo().getResolution());
        sb.append("\nDensity: ").append(crashInfo.getDeviceInfo().getDensity());
        sb.append("\nRelease: ").append(crashInfo.getDeviceInfo().getRelease());
        sb.append("\nAPI: ").append(crashInfo.getDeviceInfo().getVersion());
        sb.append("\nCPU ABI: ").append(crashInfo.getDeviceInfo().getCpuAbi());
        sb.append("\n\n");
        sb.append("-= Crash Information =-");
        String timeOfCrash = new SimpleDateFormat().format(crashInfo.getTimeOfCrash());
        sb.append("\nTime of crash: ").append(timeOfCrash);
        sb.append("\nThrowable: ").append(crashInfo.getException());
        sb.append("\nMessage: ").append(crashInfo.getExceptionMsg());
        sb.append("\nType: ").append(crashInfo.getExceptionType());
        sb.append("\nMethod: ").append(crashInfo.getMethodName());
        sb.append("\nFilename: ").append(crashInfo.getFileName());
        sb.append("\nClassname: ").append(crashInfo.getClassName());
        sb.append("\nLine number: ").append(crashInfo.getLineNumber());
        sb.append("\nFull stacktrace: ");
        sb.append("\n\n");
        sb.append(crashInfo.getFullException());

        return sb.toString();
    }
}
