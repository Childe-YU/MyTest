package com.example.cygraduation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.cygraduation.utils.Constants;
import com.example.cygraduation.utils.LogUtil;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button mLoginBtn;
    private EditText mLoginName;
    private EditText mLoginPsd;
    private String mName = null;
    private String mPsd = null;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;
    private Button mSignBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createDB();
        cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME, null);
        LogUtil.d(TAG,"LoginActivitybutton -- > " +cursor.getCount());
        initView();
        if(cursor.getCount()==0){
            dialogShow();
        }
        initEvent();
    }

    private void createDB() {
        //创建数据库
        try {
            db = SQLiteDatabase.openOrCreateDatabase(this.getDataDir().getCanonicalPath()+"/user.db", null);
            createTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        //创建数据表
        db.execSQL("create table if not exists "+ Constants.USER_TB_NAME+"("+Constants.USER_ID+" integer"
                + " primary key,"
                +Constants.USER_NAME + " varchar(50),"
                +Constants.USER_PSD +" varchar(255))");
    }

    private void dialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("注册");
        LayoutInflater layoutInflater=getLayoutInflater();
        View dg_view=layoutInflater.inflate(R.layout.dialog_login_create,null);
        builder.setView(dg_view);
        final EditText mCreateName = dg_view.findViewById(R.id.dialog_login_name_et);
        final EditText mCreatePsd = dg_view.findViewById(R.id.dialog_login_psd_et);
        builder.setPositiveButton("注册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                insertData(db, mCreateName.getText().toString().trim(), mCreatePsd.getText().toString().trim());
                cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME, null);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog= builder.create();
        dialog.show();
    }

    private void initEvent() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME, null);
                //LogUtil.d(TAG,"LoginActivitybutton -- > " +cursor.getCount());
                if(cursor.getCount()==0){
                    dialogShow();
                }
                cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME+" where "+Constants.USER_ID+" = 0",null);
                cursor.moveToFirst();
                mName = cursor.getString(cursor.getColumnIndex(Constants.USER_NAME));
                mPsd = cursor.getString(cursor.getColumnIndex(Constants.USER_PSD));
                LogUtil.d(TAG,"LoginActivitybutton -- > " +cursor.getCount());
                LogUtil.d(TAG,"LoginActivitybutton -- > " +mName+"   "+mPsd);
                if(mLoginName.getText().toString().trim().equals(mName) && mLoginPsd.getText().toString().trim().equals(mPsd)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"登录失败，请重新登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME, null);
                if(cursor.getCount()==0){
                    dialogShow();
                }
                cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME+" where "+Constants.USER_ID+" = 0",null);
                cursor.moveToFirst();
                mName = cursor.getString(cursor.getColumnIndex(Constants.USER_NAME));
                mPsd = cursor.getString(cursor.getColumnIndex(Constants.USER_PSD));
                LogUtil.d(TAG,"LoginActivitybutton -- > " +cursor.getCount());
                LogUtil.d(TAG,"LoginActivitybutton -- > " +mName+"   "+mPsd);
                if(mLoginName.getText().toString().trim().equals(mName) && mLoginPsd.getText().toString().trim().equals(mPsd)){
                    Toast.makeText(LoginActivity.this,"管理个人账号",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("账号管理");
                    LayoutInflater layoutInflater=getLayoutInflater();
                    View dg_view=layoutInflater.inflate(R.layout.dialog_login_manage,null);
                    builder.setView(dg_view);
                    final EditText mCreateName = dg_view.findViewById(R.id.dialog_login_manage_name_et);
                    final EditText mCreatePsd = dg_view.findViewById(R.id.dialog_login_manage_psd_et);
                    mCreateName.setText(mName);
                    mCreatePsd.setText(mPsd);
                    builder.setPositiveButton("更新账号信息", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateDate(db,0,mCreateName.getText().toString().trim(),mCreatePsd.getText().toString().trim());
                            LogUtil.d(TAG,"LoginActivitybutton -- > " +mName+"   "+mPsd);
                        }
                    });
                    builder.setNegativeButton("删除账号", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteData(db);
                            dialog.dismiss();
                            //cursor = db.rawQuery("select * from "+Constants.USER_TB_NAME, null);
                            dialogShow();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }else{
                    Toast.makeText(LoginActivity.this,"账号密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        mLoginBtn = this.findViewById(R.id.login_btn);
        mSignBtn = this.findViewById(R.id.sign_button);
        mLoginName = this.findViewById(R.id.login_name_et);
        mLoginPsd = this.findViewById(R.id.login_psd_et);
        //mName = "17101222";
        //mPsd = "ysc32546881";
    }

    private void insertData(SQLiteDatabase sqdb,String title, String content) {
        sqdb.execSQL("insert into tb_user values(0 ,? , ?)",
                new String[] {title, content });
    }

    private void updateDate(SQLiteDatabase sqdb,int id,String name,String password){
        sqdb.execSQL("update tb_user set name='"+name+"',password='"+password+"' where _id="+id);
    }

    private void deleteData(SQLiteDatabase sqdb){
        sqdb.execSQL("delete from tb_user");
    }
}
