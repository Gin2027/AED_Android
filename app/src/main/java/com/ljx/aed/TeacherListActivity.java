package com.ljx.aed;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ljx.aed.Utils.OKHttpUtils;
import com.ljx.aed.db.Student;
import com.ljx.aed.gson.TeacherListJsonBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherListActivity extends BaseActivity {
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);

        student = (Student) this.getIntent().getSerializableExtra("student");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        OKHttpUtils.GetTeacherList(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(TeacherListActivity.this,"获取老师列表错误",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<TeacherListJsonBean>(){}.getType();
                TeacherListJsonBean jsonBean = gson.fromJson(response.body().string(),type);
                TeacherAdapter adapter = new TeacherAdapter(TeacherListActivity.this,student,jsonBean);
                recyclerView.setAdapter(adapter);
            }
        });

    }
}
