package com.justhavehope.spinandwin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class Slider_Adapter extends PagerAdapter {

    Context context;
    LayoutInflater inflater;
    /*public int[] list_image = {
            R.drawable.a,
            R.drawable.c,
            R.drawable.b
    };*/
    public String[] list_tit = {
        "WELCOME TO SPIN AND WIN",
        "Play and earn from home",
        "Work with just your smartphone"
    };
    public String[] list_back = {
            "",
            "BACK",
            "BACK"
    };
    public String[] list_next = {
            "NEXT",
            "NEXT",
            "GET STARTED"
    };
    public String[] list_des = {
            "Spin and win is used by thousands of users from all parts of Kenya\n\nJoin us today and gain a lot",
            "This app consists just a simple wheel which you can spin your luck and win a lot.\n\nThe more you spin the more you win!!",
            "The only requirement needed is just your smartphone and you can start winning. The app is easy to use and also " +
                    "easy to win as you will see in just a few minutes"
    };
    public int[] list_bg_color = {
            Color.rgb(110, 40, 210),
            Color.rgb(45,67,193),
            Color.rgb(120,150,115)
    };
    public Slider_Adapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return list_tit.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container, false);
        ConstraintLayout constraintLayout = view.findViewById(R.id.slider_layout);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView head = view.findViewById(R.id.textView);
        TextView descr = view.findViewById(R.id.textView2);
        TextView back = view.findViewById(R.id.back);
        TextView next = view.findViewById(R.id.next);
        TextView skip = view.findViewById(R.id.skip);
        //constraintLayout.setBackgroundColor(list_bg_color[position]);
        //imageView.setImageResource(list_image[position]);
        head.setText(list_tit[position]);
        descr.setText(list_des[position]);
        back.setText(list_back[position]);
        next.setText(list_next[position]);
        container.addView(view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
