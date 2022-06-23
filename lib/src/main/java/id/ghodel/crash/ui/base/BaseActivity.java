package id.ghodel.crash.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Muhammad Mayudin on 12-Jul-21.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void initView();
    public abstract void initLogic();
    public abstract void initListener();

    protected void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
