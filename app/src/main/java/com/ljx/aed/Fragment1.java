package com.ljx.aed;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ljx.aed.db.Student;

public class Fragment1 extends Fragment {
    private TextView textView0;
    private TextView textView1;
    private Button practice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }


   @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        Student student = (Student) bundle.getSerializable("student");

        textView0 = getActivity().findViewById(R.id.textView0);
        textView1 = getActivity().findViewById(R.id.textView1);
        Typeface typeface = ResourcesCompat.getFont(getContext(),R.font.zt);
        textView0.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView1.setText(student.getName());

        practice = getActivity().findViewById(R.id.practice);
        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TraningActivity.class);
                startActivity(intent);
            }
        });
    }
}
