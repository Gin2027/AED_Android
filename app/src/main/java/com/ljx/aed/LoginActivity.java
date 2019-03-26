package com.ljx.aed;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ljx.aed.db.Student;
import com.ljx.aed.gson.LoginJsonBean;

import java.io.IOException;

import mehdi.sakout.fancybuttons.FancyButton;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText accountEdit;
    private EditText passwordEdit;
    private FancyButton login;
    private FancyButton register;
    private CheckBox rememberPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPass = findViewById(R.id.remember_pass);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        boolean isRemember = pref.getBoolean("remember_password",false);
        if (isRemember) {
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                Login(account,password);


            }
        });
    }

    public void Login(String account,String password){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("password",password)
                .build();

        Request request = new Request.Builder()
                .url("http://212.64.28.211:8080/Student/Login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Gson gson = new Gson();
                                java.lang.reflect.Type type = new TypeToken<LoginJsonBean>() {
                                }.getType();
                                LoginJsonBean jsonBean = gson.fromJson(response.body().string(), type);
                                if (jsonBean.status != true) {
                                    Toast.makeText(LoginActivity.this, jsonBean.course, Toast.LENGTH_SHORT).show();
                                } else {
                                    Student student = jsonBean.student;
                                    editor = pref.edit();
                                    if (rememberPass.isChecked()) {
                                        editor.putBoolean("remember_password", true);
                                        editor.putString("account", account);
                                        editor.putString("password", password);
                                    } else {
                                        editor.clear();
                                    }
                                    editor.apply();

                                    Toast.makeText(LoginActivity.this, jsonBean.course, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("student",student);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            } catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}