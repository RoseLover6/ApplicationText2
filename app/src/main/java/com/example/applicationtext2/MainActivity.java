package com.example.applicationtext2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mUsernumber,mPwd;
    private Button login,register;
    private static final String LOGINNUM = "17641241979";
    private static final String LOGINPWD = "521314";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsernumber = findViewById(R.id.editText1);
        mPwd = findViewById(R.id.editText2);
        login = findViewById(R.id.button1);
        register = findViewById(R.id.button2);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUsernumber.getText().toString().equals(LOGINNUM) && mPwd.getText().toString().equals(LOGINPWD)){
                    Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this , MyToolAcyivity.class);
                    intent.putExtra("userNumber" , mUsernumber.getText().toString());
                    intent.putExtra("pwd" , mPwd.getText().toString());
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this,"登录失败，账号或密码错误！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}