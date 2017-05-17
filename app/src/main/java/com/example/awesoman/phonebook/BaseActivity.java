package com.example.awesoman.phonebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by Awesome on 2016/9/29.
 */
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(setContentView());
        initView();
        setListener();
    }
    public abstract int setContentView();
    public abstract void initView();
    public abstract void setListener();

}
