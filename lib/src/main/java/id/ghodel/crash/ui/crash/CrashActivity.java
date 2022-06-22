package id.ghodel.crash.ui.crash;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import id.ghodel.crash.CrashHandler;
import id.ghodel.crash.R;
import id.ghodel.crash.data.model.CrashInfo;
import id.ghodel.crash.ui.base.BaseActivity;
import id.ghodel.crash.util.Utils;

public class CrashActivity extends BaseActivity {

    private static final int AUTO_DISMISS_MILLIS = 10000;

    public static final String CRASH_INFO = "crash_info";
    public static final String EMAIL = "email";

    private Boolean pressAgainToExit = false;
    //variable textview build information
    private TextView tvBuildVersionName, tvBuildVersionCode, tvBuildPackageName, tvBuildType;

    //variable textview device information
    private TextView tvDeviceManufacturer, tvDeviceModel, tvDeviceBrand, tvDeviceResolution, tvDeviceDensity, tvDeviceRelease, tvDeviceSDK, tvDeviceCpuAbi;

    //variable textview crash information
    private TextView tvCrashTime;
    private TextView tvCrashMessage;
    private TextView tvCrashType;
    private TextView tvCrashMethod;
    private TextView tvCrashFilename;
    private TextView tvCrashClassname;
    private TextView tvCrashLinenumber;
    private TextView tvCrashFullStacktrace;

    private CrashInfo crashInfo;


    public static void startCrash(Context context,Thread thread, Throwable throwable, CrashInfo crashInfo, String email){
        Intent intent = new Intent(context, CrashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        intent.putExtra(CRASH_INFO, crashInfo);
        intent.putExtra(EMAIL, email);

        try {
            context.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            if(CrashHandler.UNCAUGHT_EXCEPTION_HANDLER != null){
                CrashHandler.UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(thread, throwable);
            }
        }
    }

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
        //initialize view build information
        tvBuildPackageName = findViewById(R.id.tv_build_package_name);
        tvBuildVersionName = findViewById(R.id.tv_build_version_name);
        tvBuildVersionCode = findViewById(R.id.tv_build_version_code);
        tvBuildType = findViewById(R.id.tv_build_type);

        //initialize view device information
        tvDeviceManufacturer = findViewById(R.id.tv_device_manufacturer);
        tvDeviceModel = findViewById(R.id.tv_device_model);
        tvDeviceBrand = findViewById(R.id.tv_device_brand);
        tvDeviceResolution = findViewById(R.id.tv_device_resolution);
        tvDeviceDensity = findViewById(R.id.tv_device_density);
        tvDeviceRelease = findViewById(R.id.tv_device_release);
        tvDeviceSDK = findViewById(R.id.tv_device_sdk);
        tvDeviceCpuAbi = findViewById(R.id.tv_device_cpu_abi);

        //initialize view crash information
        tvCrashTime = findViewById(R.id.tv_crash_time);
        tvCrashMessage = findViewById(R.id.tv_crash_message);
        tvCrashType = findViewById(R.id.tv_crash_type);
        tvCrashMethod = findViewById(R.id.tv_crash_method);
        tvCrashFilename = findViewById(R.id.tv_crash_filename);
        tvCrashClassname = findViewById(R.id.tv_crash_classname);
        tvCrashLinenumber =  findViewById(R.id.tv_crash_line_number);
        tvCrashFullStacktrace =  findViewById(R.id.tv_crash_full_stacktrace);

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void initLogic() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        crashInfo = getIntent().getParcelableExtra(CRASH_INFO);
        String email = getIntent().getStringExtra(EMAIL);

        //build information
        tvBuildVersionCode.setText(crashInfo.getVersionCode());
        tvBuildVersionName.setText(crashInfo.getVersionName());
        tvBuildPackageName.setText(crashInfo.getPackageName());
        tvBuildType.setText(crashInfo.getBuildType());

        //device information
        tvDeviceManufacturer.setText(crashInfo.getDeviceInfo().getManufacturer());
        tvDeviceModel.setText(crashInfo.getDeviceInfo().getModel());
        tvDeviceBrand.setText(crashInfo.getDeviceInfo().getBrand());
        tvDeviceResolution.setText(crashInfo.getDeviceInfo().getResolution());
        tvDeviceDensity.setText(crashInfo.getDeviceInfo().getDensity());
        tvDeviceRelease.setText(crashInfo.getDeviceInfo().getRelease());
        tvDeviceSDK.setText(crashInfo.getDeviceInfo().getVersion());
        tvDeviceCpuAbi.setText(crashInfo.getDeviceInfo().getCpuAbi());

        //crash information

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvCrashTime.setText(new SimpleDateFormat().format(crashInfo.getTimeOfCrash()));
        }

        tvCrashMessage.setText(crashInfo.getExceptionMsg());
        tvCrashType.setText(crashInfo.getExceptionType());
        tvCrashMethod.setText(crashInfo.getMethodName());
        tvCrashFilename.setText(crashInfo.getFileName());
        tvCrashClassname.setText(crashInfo.getClassName());
        tvCrashFullStacktrace.setText(crashInfo.getFullException());
        tvCrashLinenumber.setText(String.valueOf(crashInfo.getLineNumber()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actiion_stackoverflow) {
            Utils.goToStackoverflow(CrashActivity.this, crashInfo.getExceptionMsg());
        } else if (id == R.id.actiion_share) {
            Utils.shareError(CrashActivity.this, crashInfo.getFullException());
        }else if(id == R.id.actiion_report){


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initListener() {
        /*imgStackOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            }
        });
        imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           }
        });*/
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
                Utils.showToast(CrashActivity.this, ""+TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1, Utils.LENGTH_SHORT);
            }
            @Override
            public void onFinish() {
                Utils.restartApp(CrashActivity.this);
            }
        }.start();
    }
}