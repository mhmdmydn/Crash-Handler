package id.ghodel.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Muhammad Mayudin on 12-Jul-21.
 */
public class CrashUtil {

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
}
