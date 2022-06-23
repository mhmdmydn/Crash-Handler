package id.ghodel.crash.ui.crash;

import static id.ghodel.crash.CrashHandler.CRASH_INFO;
import static id.ghodel.crash.CrashHandler.EMAIL;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import id.ghodel.crash.data.model.CrashInfo;
import id.ghodel.crash.ui.base.BaseActivity;
import id.ghodel.crash.util.Utils;

public class CrashDialogActivity extends BaseActivity {

    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initLogic();
        initListener();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initLogic() {
        CrashInfo crashInfo = getIntent().getParcelableExtra(CRASH_INFO);
        email = getIntent().getStringExtra(EMAIL);

        showCrash(crashInfo);
    }

    @Override
    public void initListener() {

    }

    private void showCrash(CrashInfo crashInfo){
        final String crashInfoString = Utils.parseCrashInfoToString(crashInfo);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(crashInfoString);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.copy, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.copyCrashToClipboard(CrashDialogActivity.this, "CrashInfo", crashInfoString);
                toast("Successfully copy crash to clipboard");
                finish();
            }
        });
        builder.setNeutralButton("Report", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Utils.sendCrashToMail(CrashDialogActivity.this, email, crashInfoString);
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}
