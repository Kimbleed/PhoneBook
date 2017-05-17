package com.example.awesoman.phonebook.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.awesoman.phonebook.BaseActivity;
import com.example.awesoman.phonebook.R;
import com.example.awesoman.phonebook.SQLite.PhoneSQLiteHepler;
import com.example.awesoman.phonebook.SQLite.TypeEntry;

public class LongClickActivity extends BaseActivity {
    String phoneType ;
    String phoneName ;
    String phoneNum;
    @Override
    public int setContentView() {
        return R.layout.activity_long_click;
    }

    @Override
    public void initView() {
        phoneType = getIntent().getExtras().getString("phoneType");
        phoneName = getIntent().getExtras().getString("phoneName");
        phoneNum = getIntent().getExtras().getString("phoneNum");
    }

    @Override
    public void setListener() {

    }
    public void updatePhone(View view){
       Intent intent = new Intent();
        intent.setClass(this,AddOrUpdatePhoneNumActivity.class);
        intent.putExtra("execute", "update");
        intent.putExtra("phoneType",phoneType);
        intent.putExtra("phoneName",phoneName);
        intent.putExtra("phoneNum",phoneNum);
        startActivity(intent);
        finish();
    }
    public void deletePhone(View view){

        PhoneSQLiteHepler mHelper  = new PhoneSQLiteHepler(this);
        Log.i("deletePhone",phoneType);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(phoneType,"name = '"+phoneName+"'",null);
        finish();
    }


}
