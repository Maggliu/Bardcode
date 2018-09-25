package com.example.liudingming.barcode;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private EditText source;
    private EditText check;
    private ImageButton image;
    private EditText noise;
    private Button noiseButton;
    private int[] sorceArray;
    private int checknum;
    private DrawBmp drawBmp;
    private ArrayList<int[]> sorcecode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }
    private void initView(){
        source=(EditText)findViewById(R.id.souce);
        check=(EditText)findViewById(R.id.check);
        image=(ImageButton)findViewById(R.id.image);
        noise=(EditText)findViewById(R.id.noise);
        noiseButton=(Button)findViewById(R.id.noisebutton);
        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                int temp=source.getText().length();
                if(temp==12){
                    try{
                        long sorcenum=Long.parseLong(source.getText().toString());
                        sorceArray=new int[13];
                        for(int i=0;i<12;i++){
                            Log.d("sorce",sorcenum+"");
                            sorceArray[11-i]=(int)(sorcenum%10);
                            sorcenum/=10;
                        }
                        checknum=Code.getCheck(sorceArray);
                        Log.d("check",checknum+"");
                        sorceArray[12]=checknum;
                        Code code=new Code(sorceArray);
                        sorcecode=code.getCode();
                        check.setText(checknum+"");
                        drawBmp=new DrawBmp(sorcecode);
                        image.setImageBitmap(drawBmp.drawPicture());
                    }catch (NumberFormatException e)
                    {}
                }
            }
        });
        noiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawBmp!=null){
                int temp=Integer.parseInt(noise.getText().toString());
                image.setImageBitmap(drawBmp.addNoise(temp));}
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawBmp!=null)
                image.setImageBitmap(drawBmp.drawPicture());
            }
        });
    }
}
