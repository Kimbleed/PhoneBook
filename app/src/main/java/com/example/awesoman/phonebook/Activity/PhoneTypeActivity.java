package com.example.awesoman.phonebook.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.awesoman.phonebook.Adapter.PhoneTypeAdapter;
import com.example.awesoman.phonebook.BaseActivity;
import com.example.awesoman.phonebook.Entity.PhoneTypeEntity;
import com.example.awesoman.phonebook.R;
import com.example.awesoman.phonebook.SQLite.PhoneSQLiteHepler;
import com.example.awesoman.phonebook.SQLite.TypeEntry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class imPhoneTypeActivity extends BaseActivity {

    final String[] titles = new String[]{"订餐电话","公共服务","快递电话"};

    TextView tv_title;
    ListView lv_phone_type;
    List<PhoneTypeEntity>data;
    PhoneTypeAdapter ptAdapter;
    Handler mHandler ;
    static int i=0;


    @Override
    public int setContentView() {
        return R.layout.activity_phone_type;
    }

    @Override
    public void initView() {
        tv_title = (TextView)findViewById(R.id.tv_phone_type);
        tv_title.setText(titles[0]);
        lv_phone_type = (ListView)findViewById(R.id.lv_phone_type);
        ptAdapter = new PhoneTypeAdapter(this);
        lv_phone_type.setAdapter(ptAdapter);


        initHandler();
        importDatabase();
        loadListView(0);
//        changePhoneType();
        setAnimation();
        freshInTime();
    }


    /**
     * ListView设置监听，点击弹出提示框，确认打电话
     */
    @Override
    public void setListener() {
        lv_phone_type.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(PhoneTypeActivity.this, LongClickActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("phoneType", data.get(i).getPhoneType());
                bundle.putString("phoneName", data.get(i).getPhoneName());
                bundle.putString("phoneNum", data.get(i).getPhoneNum());
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            }
        });
        lv_phone_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(PhoneTypeActivity.this)
                        .setTitle("警告")//标题
                        .setMessage(
                                "是否开始拨打"
                                        + data.get(i).getPhoneName()
                                        + "电话"
                                        + "\n"
                                        + "TEL:" + data.get(i).getPhoneNum())//对话框信息
                        .setPositiveButton(
                                "拨号",
                                //点击拨号后的监听
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        requestPermission(i);
                                    }
                                })//确认按钮
                        .setNegativeButton("取消", null)//取消按钮
                        .show();//显示警告框
            }
        });
    }

    /**
     *
     * @param position 点击的位置
     */
    private void requestPermission(int position) {
        //检查是否拥有外部存储器写权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    0);
        }
        else
        {
            call(position);
        }

    }

    /**
     *
     * @param position 点击的位置
     */
    public void call(int position){
        Log.i("call", data.get(position).getPhoneNum());
        //拨打当前点击的电话号码
        Intent intent = new Intent(
                "android.intent.action.CALL",
                Uri.parse("tel:" + data.get(position).getPhoneNum()));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //判断申请码
        switch (requestCode) {
            case 0:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //申请的第一个权限失败后
                    finish();
                }

                break;
        }
    }

    /**
     * description:检测数据库是否存在，无则从APK中提供数据库
     */

    private void importDatabase() {
        try {
            Log.i("importDatabase","start");
            //创建数据库目录，若数据库目录不存在，创建单层目录
            File dirFile = new File(TypeEntry.DATABASE_PATH);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            //创建将被导入的数据库File对象
            File file = new File(TypeEntry.DATABASE_PATH, PhoneSQLiteHepler.DATABASE_NAME);
            //判断文件是否存在，如不存在则创建该文件，存在就直接返回
            if (!file.exists()) {
                file.createNewFile();

                //获得自带数据库的输入流
                InputStream ip = getResources().openRawResource(R.raw.phone);
                //创建将被导入的数据库输出流
                FileOutputStream fop = new FileOutputStream(file);
                //创建缓冲区
                byte[] buffer = new byte[1024];
                int count =0;

                //将数据读入缓冲区，并写入输出流
                while ((count =ip.read(buffer)) != -1) {
                    //将缓冲区中的数据写入输出流
                    fop.write(buffer, 0, count);
                    Log.i("i", count + "");
                    //重置缓冲区
                    fop.flush();
                }
                //关闭输入输出流
                ip.close();
                fop.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * description:从数据据中读取电话表数据
     * @param a 1.订餐2.快递3.公共服务
     * @return 加载到主屏幕的电话数据
     */
    private List<PhoneTypeEntity> readTable(int a){
        PhoneSQLiteHepler mHelper  = new PhoneSQLiteHepler(this);
        List<PhoneTypeEntity> typeEntities =new ArrayList<>();
        Log.i("readTable","start");

        SQLiteDatabase db = mHelper.getReadableDatabase();
        //数据库操作select
        String sql ="select * from "+ TypeEntry.PhoneTypes[a];
        Log.i("readTable",sql);

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount()>=1) {
            cursor.moveToFirst();
            //加载实例
            do {

                PhoneTypeEntity pte = new PhoneTypeEntity();
                pte.setPhoneName(cursor.getString(cursor.getColumnIndex(TypeEntry.COLUMNS_PHONETYPE_NAME)));
                pte.setPhoneNum(cursor.getString(cursor.getColumnIndex(TypeEntry.COLUMNS_PHONETYPE_NUM)));
                pte.setPhoneType(TypeEntry.PhoneTypes[i]);
                Log.i("TypeEntry", (cursor.getString(cursor.getColumnIndex(TypeEntry.COLUMNS_PHONETYPE_NAME)) + "\t" + cursor.getString(cursor.getColumnIndex(TypeEntry.COLUMNS_PHONETYPE_NUM))));
                typeEntities.add(pte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return typeEntities;
    }



    public void jumpToAddPhoneNum(View view){
        Intent intent  = new Intent();
        intent.setClass(this, AddOrUpdatePhoneNumActivity.class);
        intent.putExtra("phoneType", TypeEntry.PhoneTypes[i]);
        Log.i("jumpToAddPhoneNum",titles[i]);
        intent.putExtra("execute","add");
        startActivity(intent);

    }

    /**
     * description:加载数据库中电话表
     * @param i 1.订餐2.快递3.公共服务
     */

    public void loadListView(int i){
        data =    readTable(i);
        ptAdapter.fresh(data);
        ptAdapter.notifyDataSetChanged();
    }


    /**
     * 当操作完加或修改后，回到该Activity，依旧保持该页
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume", "start");
        loadListView(i);
    }

    /**
     * description:设置ListView过渡动画
     */
    public void setAnimation(){

        Animation itemRightInAnim = AnimationUtils.loadAnimation(this, R.anim.rightin);
        //listView动画
        LayoutAnimationController listRightInAnim = new LayoutAnimationController(itemRightInAnim);
        //动画间隔
        listRightInAnim .setDelay(0.5f);

        lv_phone_type.setLayoutAnimation(listRightInAnim);

    }



    /**
     * @10.10
     * description:定时刷新电话表，并发送数据给Handler
     */
    public void freshInTime(){
        Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    i = (i + 1) % 3;
                    Message mMessage = Message.obtain();
                    mMessage.obj = i + "";
                    mHandler.sendMessage(mMessage);
                }
            }, 3000, 3000);
    }



    /**
     * @10.10
     * description:初始化Handler，用于并发处理刷新电话表修改UI
     */
    public void initHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String str =String.valueOf(msg.obj);
                loadListView(Integer.parseInt(str));
                tv_title.setText(titles[Integer.parseInt(str)]);
                lv_phone_type.startLayoutAnimation();
            }
        };
    }

    /**
     * description:旧版定时器
     */
    public void changePhoneType(){

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                        i = (i + 1) % 3;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_title.setText(titles[i]);
                                loadListView(i);
//                                lv_phone_type.startLayoutAnimation();
                            }
                        });
                    } catch (Exception e) {

                    }

                }
            }
        }).start();
    }

}
