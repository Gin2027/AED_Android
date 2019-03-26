package com.ljx.aed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ljx.aed.Utils.OKHttpUtils;
import com.ljx.aed.db.Student;
import com.ljx.aed.db.Teacher;
import com.ljx.aed.gson.TeacherListJsonBean;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
    private Context mcontext;
    private Student mstudent;
    private TeacherListJsonBean mjsonBean;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView introduce;
        TextView phone;
        Button setTeacher;

        public ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.teacher_name);
            introduce = view.findViewById(R.id.teacher_introduce);
            phone = view.findViewById(R.id.teacher_phone);
            setTeacher = view.findViewById(R.id.set_teacher);
        }
    }

    public TeacherAdapter(Context context,Student student,TeacherListJsonBean jsonBean){
        mcontext = context;
        mstudent = student;
        mjsonBean = jsonBean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Teacher teacher = mjsonBean.teacherList.get(position);
        holder.name.setText("姓名:"+teacher.getName());
        holder.introduce.setText("简介:"+teacher.getIntroduce());
        holder.phone.setText("联系方式:"+teacher.getPhone());

        holder.setTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("Warning");
                dialog.setMessage("你确定要选择该老师吗?一旦选择将不能更改!");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        OKHttpUtils.ChooseTeacher(mstudent.getId(), teacher.getId(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Looper.prepare();
                                Toast.makeText(view.getContext(),"选择失败",Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                mstudent.setTid(teacher.getId());
                                Intent intent = new Intent(mcontext,MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("student",mstudent);
                                intent.putExtras(bundle);
                                mcontext.startActivity(intent);
                            }
                        });
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
    }

    @Override
    public int getItemCount() {
        return mjsonBean.count;
    }
}
