package com.ljx.aed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ljx.aed.Utils.OKHttpUtils;
import com.ljx.aed.db.Student;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Fragment3 extends Fragment {
    private TextView stumessage;
    private Button modify;
    private Button cancel;
    private Button version;
    private Button call;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stumessage = getActivity().findViewById(R.id.studentmessage);
        modify = getActivity().findViewById(R.id.modify);
        cancel = getActivity().findViewById(R.id.cancel);
        version = getActivity().findViewById(R.id.version);
        call = getActivity().findViewById(R.id.call);

        Bundle bundle = this.getArguments();
        Student student = (Student) bundle.getSerializable("student");
        stumessage.setText("个人信息\n"+student.getName()+"\n"+student.getLocation()+"\n"+student.getSchool()+"\n"+student.getPhone()+"\n"+student.getQq());

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("修改密码");
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.newpassword,null);
                dialog.setView(view1);

                EditText oldText = view1.findViewById(R.id.old);
                EditText new1Text = view1.findViewById(R.id.new1);
                EditText new2Text  = view1.findViewById(R.id.new2);

                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String old = oldText.getText().toString();
                        String new1 = new1Text.getText().toString();
                        String new2 = new2Text.getText().toString();

                        if (new1.equals(new2)) {
                            OKHttpUtils.ModifyStudent(student.getId(), old, new1, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String result = response.body().string();

                                    Looper.prepare();
                                    Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            });
                        } else {
                            Toast.makeText(getActivity(),"两次输入不一致",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Warning");
                dialog.setMessage("你确定要注销吗？");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent("com.ljx.aed.FORCE_OFFLINE");
                        getActivity().sendBroadcast(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });

        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("版本信息");
                dialog.setMessage("Ver1.0 By ljx");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("联系方式");
                dialog.setMessage("邮箱:jxli_2027@stu.xidian.edu.cn\nQQ:952462054");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }
}
