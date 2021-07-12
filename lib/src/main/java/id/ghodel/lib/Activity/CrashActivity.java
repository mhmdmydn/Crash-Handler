package id.ghodel.lib.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import id.ghodel.lib.R;
import id.ghodel.lib.utils.CrashUtil;

public class CrashActivity extends BaseActivity {

    private static final int AUTO_DISMISS_MILLIS = 10000;

    public static final String CRASHINFO = "crashInfo";
    public static final String CRASHCAUSE = "cause";
    public static final String EMAIL = "email";
    public static final String BACKGROUND = "background";

    //variable dump device info
    public static final String DATE = "id.ghodel.lib.DATE";
    public static final String MANUFACTURER = "id.ghodel.lib.MANUFACTURER";
    public static final String MODEL = "id.ghodel.lib.MODEL";
    public static final String VERSION = "id.ghodel.lib.VERSION";
    public static final String SDK = "id.ghodel.lib.SDKINFO";

    //variable dump apk info
    public static final String PACKAGENAME = "id.ghodel.lib.PACKAGENAME";
    public static final String APPVERSIONNAME = "id.ghodel.lib.APPVERSIONNAME";
    public static final String APPVERSIONCODE = "id.ghodel.lib.APPVERSIONCODE";


    private Boolean pressAgainToExit = false;

    private AppCompatTextView  tvDate, tvManufacturer, tvModel, tvVersion, tvSDKinfo, tvPackageName, tvVersionName, tvVersionCode, tvException;
    private AppCompatImageView imgShare, imgReport, imgStackOverFlow;
    private ScrollView scrollBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);

        initView();
        initLogic();
        initListener();
    }

    @Override
    public void initView() {
        tvException = findViewById(R.id.tv_result_exception);
        tvDate = findViewById(R.id.tv_date);
        tvManufacturer = findViewById(R.id.tv_manufacturer);
        tvModel = findViewById(R.id.tv_model);
        tvVersion = findViewById(R.id.tv_version);
        tvSDKinfo = findViewById(R.id.tv_sdk);

        tvPackageName = findViewById(R.id.tv_package_name);
        tvVersionCode = findViewById(R.id.tv_version_code);
        tvVersionName = findViewById(R.id.tv_version_name);

        imgReport = findViewById(R.id.img_report);
        imgShare = findViewById(R.id.img_share);
        imgStackOverFlow = findViewById(R.id.img_stackoverflow);

        scrollBg = findViewById(R.id.scroll_bg);
    }

    @Override
    public void initLogic() {
        getSupportActionBar().hide();


        tvDate.setText(getIntent().getStringExtra(DATE) != null? getIntent().getStringExtra(DATE) : "-");
        tvManufacturer.setText(getIntent().getStringExtra(MANUFACTURER) != null? getIntent().getStringExtra(MANUFACTURER) : "-");
        tvModel.setText(getIntent().getStringExtra(MODEL) != null? getIntent().getStringExtra(MODEL) : "-");
        tvVersion.setText(getIntent().getStringExtra(VERSION) != null? getIntent().getStringExtra(VERSION) : "-");
        tvSDKinfo.setText(getIntent().getStringExtra(SDK) != null? getIntent().getStringExtra(SDK) : "-");


        tvPackageName.setText(getIntent().getStringExtra(PACKAGENAME) != null? getIntent().getStringExtra(PACKAGENAME) : "-");
        tvVersionName.setText(getIntent().getStringExtra(APPVERSIONNAME) != null? getIntent().getStringExtra(APPVERSIONNAME) : "-");
        tvVersionCode.setText(getIntent().getStringExtra(APPVERSIONCODE) != null? getIntent().getStringExtra(APPVERSIONCODE) : "-");

        tvException.setText(getIntent().getStringExtra(CRASHINFO) != null? getIntent().getStringExtra(CRASHINFO) : "-");

        if(getIntent().getStringExtra(BACKGROUND) != null){
            CrashUtil.changeBackgroundColor(scrollBg, getIntent().getStringExtra(BACKGROUND));
        } else {
            CrashUtil.changeBackgroundColor(scrollBg, "#E2E9FD");
        }

    }

    @Override
    public void initListener() {
        imgStackOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashUtil.goToStackoverflow(CrashActivity.this, getIntent().getStringExtra(CRASHCAUSE) != null? getIntent().getStringExtra(CRASHCAUSE) : "-");
            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashUtil.shareError(CrashActivity.this, getIntent().getStringExtra(CRASHINFO) != null? getIntent().getStringExtra(CRASHINFO) : "-");
            }
        });
        imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrashUtil.goToEmailCrashBugReport(CrashActivity.this, getIntent().getStringExtra(EMAIL) != null? getIntent().getStringExtra(EMAIL) : "-", getIntent().getStringExtra(CRASHINFO) != null? getIntent().getStringExtra(CRASHINFO) : "-");
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(pressAgainToExit){
            super.onBackPressed();
        }
        this.pressAgainToExit = true;
        showToast("Press back again to exit");
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                pressAgainToExit = false;
            }
        }, 2000);
    }

    private void showToast(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void countDownRestart(){
        new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                CrashUtil.showToast(CrashActivity.this, ""+TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1, CrashUtil.LENGTH_SHORT);
            }
            @Override
            public void onFinish() {
                CrashUtil.restartApp(CrashActivity.this);
            }
        }.start();
    }
}