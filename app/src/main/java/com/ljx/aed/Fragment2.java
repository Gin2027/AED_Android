package com.ljx.aed;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ljx.aed.Utils.OKHttpUtils;
import com.ljx.aed.db.Student;
import com.ljx.aed.db.Teacher;
import com.ljx.aed.gson.AdviceListJsonBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment2  extends Fragment {
    private Student student;
    private Teacher teacher;
    private Button choose;
    private Button send;
    private TextView teachermessage1;
    private TextView teachermessage2;
    private TextView notice;
    private EditText editText;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        choose = getActivity().findViewById(R.id.choose);
        send = getActivity().findViewById(R.id.send);
        teachermessage1 = getActivity().findViewById(R.id.teachermessage1);
        teachermessage2 = getActivity().findViewById(R.id.teachermessage2);
        notice = getActivity().findViewById(R.id.notice);
        editText = getActivity().findViewById(R.id.advise);
        listView = getActivity().findViewById(R.id.ReplyListView);

        Bundle bundle = this.getArguments();
        student = (Student) bundle.getSerializable("student");

        OKHttpUtils.GetReplies(student.getId(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<AdviceListJsonBean>(){}.getType();
                AdviceListJsonBean jsonBean = gson.fromJson(response.body().string(),type);
                ReplyAdapter adapter = new ReplyAdapter(getContext(),R.layout.reply_item,jsonBean.getAdviceList());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listView.setAdapter(adapter);
                    }
                });
            }
        });

        if (student.getTid()==0) {
            teachermessage1.setText("暂未绑定老师");
        } else {
            OKHttpUtils.GetTeacher(student.getTid(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "获取教师信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    teacher = new Gson().fromJson(response.body().string(), Teacher.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            teachermessage1.setText("我的老师  "+teacher.getName()+"\n");
                            teachermessage2.setText("老师介绍 "+teacher.getIntroduce()+"\n"+"联系方式 "+teacher.getPhone()+"\n");
                            notice.setText("最新公告:"+teacher.getNotice());
                        }
                    });
                }
            });
        }



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.getTid()!=0) {
                    Toast.makeText(getActivity(),"你已经选择了老师,不可再更改!",Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(),TeacherListActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("student",student);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (student.getTid()==0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("错误")
                            .setMessage("请先选择老师")
                            .setPositiveButton("确定",null)
                            .show();
                } else {
                    if (TextUtils.isEmpty(editText.getText())) {
                        Toast.makeText(getActivity(),"内容不能为空",Toast.LENGTH_SHORT).show();
                    } else {
                        OKHttpUtils.SendAdvice(student.getId(), editText.getText().toString(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(getActivity())
                                                .setMessage("你已成功留言")
                                                .setPositiveButton("确定",null)
                                                .show();
                                    }
                                });
                            }
                        });
                    }
                }

            }
        });

    }
}