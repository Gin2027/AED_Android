package com.ljx.aed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ljx.aed.Utils.OKHttpUtils;

public class RegisterActivity extends BaseActivity {
    private String result;
    private EditText account;
    private EditText password;
    private EditText name;
    private EditText age;
    private EditText location;
    private EditText school;
    private EditText phone;
    private EditText qq;
    private Button put;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        location = findViewById(R.id.location);
        school = findViewById(R.id.school);
        phone = findViewById(R.id.phone);
        qq = findViewById(R.id.qq);
        put = findViewById(R.id.button);

        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ac = account.getText().toString();
                String pa = password.getText().toString();
                String na = name.getText().toString();
                String ag =  age.getText().toString();
                String lo = location.getText().toString();
                String sc = school.getText().toString();
                String ph = phone.getText().toString();
                String q = qq.getText().toString();


                if (Check(ac,pa,ph)!="yes") {
                    Toast.makeText(RegisterActivity.this,Check(ac,pa,ph),Toast.LENGTH_SHORT).show();
                } else {
                    OKHttpUtils okHttpUtils = new OKHttpUtils();
                    okHttpUtils.Register(ac,pa,na,ag,lo,sc,ph,q);
                    okHttpUtils.setOnOKHttpPostListener(new OKHttpUtils.OKHttpPostListener() {
                        @Override
                        public void error(String error) {
                            Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void success(String json) {
                            Toast.makeText(RegisterActivity.this,json,Toast.LENGTH_SHORT).show();
                            if (json=="注册成功") {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    public String Check(String ac,String pa,String ph){
        if (ac.length()<6 || ac.length()>16) return "账号长度不符合规范";
        if (pa.length()<6) return "密码过弱";
        if (pa.length()>16) return "密码过长";
        if (ph.length()!=11) return "手机号长度错误";
        return "yes";
    }
}
