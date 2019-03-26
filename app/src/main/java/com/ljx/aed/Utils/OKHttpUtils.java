package com.ljx.aed.Utils;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OKHttpUtils {
    private final String url = "http://212.64.28.211:8080/Student/";
    private OKHttpPostListener onOKHttpPostListener;
    private MyHandler myHandler = new MyHandler();
    private static final String TAG = "OKHttpUtils";
    public void Register(String account,String password,String name,String age,String location,String school,String phone,String qq) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("account",account)
                .add("password",password)
                .add("name",name)
                .add("age",age)
                .add("location",location)
                .add("school",school)
                .add("phone",phone)
                .add("qq",qq)
                .build();

        Request request = new Request.Builder()
                .url(url+"Register")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = myHandler.obtainMessage();
                message.obj = "Post请求失败";
                message.what = 0;
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = myHandler.obtainMessage();
                String json = response.body().string();
                message.obj = json;
                message.what = 1;
                myHandler.sendMessage(message);
            }
        });
    }

    public interface OKHttpPostListener{
        void error(String error);
        void success(String json);
    }

    public void setOnOKHttpPostListener(OKHttpPostListener onOKHttpPostListener){
        this.onOKHttpPostListener = onOKHttpPostListener;
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int w =msg.what;
            if (w==0) {
                String error = (String)msg.obj;
                onOKHttpPostListener.error(error);
            }
            if (w==1) {
                String json = (String) msg.obj;
                onOKHttpPostListener.success(json);
            }
        }
    }

    public static void GetTeacher(int tid,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().add("tid",tid+"").build();
        Request request = new Request.Builder()
                .url("http://212.64.28.211:8080/Teacher/Get")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void GetTeacherList(Callback callback){
        OkHttpClient client = new OkHttpClient();
        String url = "http://212.64.28.211:8080/GetAllTeachers";
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }

    public static void ChooseTeacher(int sid,int tid,Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                            .add("sid",sid+"")
                            .add("tid",tid+"")
                            .build();
        Request request = new Request.Builder()
                            .url("http://212.64.28.211:8080/Student/Choose")
                            .post(body)
                            .build();
        client.newCall(request).enqueue(callback);
    }

    public static void SendAdvice(int sid,String message,Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                                .add("sid",sid+"")
                                .add("message",message)
                                .build();
        Request request = new Request.Builder()
                                .url("http://212.64.28.211:8080/Student/GiveAdvice")
                                .post(body)
                                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void ModifyStudent(int id,String oldpw,String newpw,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                                .add("id",id+"")
                                .add("old",oldpw)
                                .add("new",newpw)
                                .build();
        Request request = new Request.Builder()
                                .url("http://212.64.28.211:8080/Student/Modify")
                                .post(body)
                                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void GetReplies(int sid,Callback callback){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("sid",sid+"")
                .build();
        Request request = new Request.Builder()
                .url("http://212.64.28.211:8080/Student/GetReplies")
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
