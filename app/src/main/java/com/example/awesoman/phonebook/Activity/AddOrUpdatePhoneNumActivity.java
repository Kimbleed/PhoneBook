package com.example.awesoman.phonebook.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.awesoman.phonebook.BaseActivity;
import com.example.awesoman.phonebook.R;
import com.example.awesoman.phonebook.SQLite.PhoneSQLiteHepler;
import com.example.awesoman.phonebook.SQLite.TypeEntry;

public class AddOrUpdatePhoneNumActivity extends BaseActivity {

    TextView tv_operate;
    EditText et_phone_name;
    EditText et_phone_num;
    Button btn_execute;
    LinearLayout ll_operate;
    String bundle_phoneNum;
    String bundle_phoneName;
    @Override
    public int setContentView() {
        return R.layout.activity_add_phone_num;
    }

    @Override
    public void initView() {
        ll_operate=(LinearLayout)findViewById(R.id.ll_operate);
        tv_operate=(TextView)findViewById(R.id.tv_operate);
        et_phone_name=(EditText)findViewById(R.id.et_phone_name);
        et_phone_num=(EditText)findViewById(R.id.et_phone_num);
        btn_execute =(Button)findViewById(R.id.btn_execute);
        //获取从那个Activity跳转过来 add:增加  update:修改
        String execute =getIntent().getExtras().getString("execute");
        switch(execute){
            case "add":
                tv_operate.setText("添加电话");
                btn_execute.setText("添加");
                break;
            case "update":
                tv_operate.setText("修改电话");
                btn_execute.setText("保存");
                bundle_phoneNum = getIntent().getExtras().getString("phoneNum");
                bundle_phoneName = getIntent().getExtras().getString("phoneName");
                et_phone_name.setText(bundle_phoneName);
                et_phone_num.setText(bundle_phoneNum);
                break;
        }
    }

    @Override
    public void setListener() {
        ll_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputMethodCheck(view);
                lostFocus();
            }
        });
    }
    public void clickForPhone(View view){
        //获取从执行何种操作
        String phoneType = getIntent().getExtras().getString("phoneType");
        if(btn_execute.getText().toString().equals("添加"))
            addPhone(phoneType);
        else
            updatePhone(phoneType);
    }
    public void addPhone(String phoneType){
        //获取用户输入信息
        String str_phoneNum = et_phone_num.getText().toString();
        String str_phoneName = et_phone_name.getText().toString();
        Log.i("addPhone",phoneType);
        PhoneSQLiteHepler mHelper  = new PhoneSQLiteHepler(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TypeEntry.COLUMNS_PHONETYPE_NUM,str_phoneNum);
        contentValues.put(TypeEntry.COLUMNS_PHONETYPE_NAME,str_phoneName);
        db.insert(phoneType, null, contentValues);
        finish();
    }

    public void updatePhone(String phoneType){
        //获取用户输入信息
        String str_phoneNum = et_phone_num.getText().toString();
        String str_phoneName = et_phone_name.getText().toString();
        Log.i("updatePhoneName",str_phoneName);
        Log.i("updatePhoneNum",str_phoneNum);
        PhoneSQLiteHepler mHelper  = new PhoneSQLiteHepler(this);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TypeEntry.COLUMNS_PHONETYPE_NUM,str_phoneNum);
        contentValues.put(TypeEntry.COLUMNS_PHONETYPE_NAME,str_phoneName);
        db.update(phoneType, contentValues, TypeEntry.COLUMNS_PHONETYPE_NAME + "='" +  bundle_phoneName+ "'", null);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("life", "onActivityResult");

    }

    public void lostFocus(){
        ll_operate.setFocusable(true);
        ll_operate.setFocusableInTouchMode(true);
        ll_operate.requestFocus();
    }
    public void inputMethodCheck(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      if (imm.isActive())//当搜索栏被focues的时候执行隐藏键盘
        {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
