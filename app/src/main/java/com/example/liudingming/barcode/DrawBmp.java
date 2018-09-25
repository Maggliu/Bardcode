package com.example.liudingming.barcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by LiuDingming on 2018/5/21.
 */

public class DrawBmp {
    private Bitmap photo;
    private Canvas myCanvas;
    private Paint myPaint;
    int totalWide;//总宽
    int totalHigh;//总高
    int leftPadding=20;//条形码和整图左右两边的距离
    int upPadding=10;//条形码和整图上下两边的距离
    int leftBegin=20;//画图启始位
    int upBegin=10;
    final static int TOTALCODE=95;//总的比特数，EAN13包括警戒条共有95位
    int perCodeWide=11;//每个单位条的宽度
    int codeHigh=600;//每个单位条的高度
    String savePath;//存储路径
    ArrayList<int[]> code;
    int[] oneCode;
    public DrawBmp(ArrayList<int[]> code){//多个构造函数，可输入不同的参数
        this.code=code;
        codeToOneCode();//将用ArrayList列表存储的编码转换为一维的数组
        this.totalHigh=this.codeHigh+upPadding*2;//计算总高和总长
        this.totalWide=this.perCodeWide*TOTALCODE+leftPadding*2;
        photo=Bitmap.createBitmap(totalWide,totalHigh, Bitmap.Config.ARGB_8888);
        myCanvas=new Canvas(photo);
        initPaint();
    }
    public DrawBmp(int perCodeWide,int codeHigh,ArrayList<int[]> code){
        this.codeHigh=codeHigh;
        this.perCodeWide=perCodeWide;
        this.code=code;
        codeToOneCode();
        this.totalHigh=this.codeHigh+upPadding*2;
        this.totalWide=this.perCodeWide*TOTALCODE+leftPadding*2;
        photo=Bitmap.createBitmap(totalWide,totalHigh, Bitmap.Config.ARGB_8888);
        myCanvas=new Canvas(photo);
        initPaint();
    }

    private void codeToOneCode(){//转换为一维数组，方便画图
        int temp=0;
        oneCode=new int[95];

        for(int i=0;i<code.size();i++){
            for(int x=0;x<code.get(i).length;x++){//将编码填入一维数组中
                oneCode[temp]=code.get(i)[x];
                temp++;
            }
        }
    }
    public void initPaint(){
        myPaint=new Paint();
        myPaint.setColor(Color.BLACK);
        myPaint.setStrokeWidth(perCodeWide);
    }
    public Bitmap drawPicture(){
        int leftBeginTemp=leftBegin;
        myCanvas.drawColor(Color.WHITE);//先全部涂白
        for(int i=0;i<oneCode.length;i++){
            if(oneCode[i]==1)//码组中为一的在图片中涂一条黑线，宽度在构造函数中设定
            myCanvas.drawLine(leftBeginTemp,upPadding,leftBeginTemp,totalHigh-upPadding,myPaint);
            leftBeginTemp+=perCodeWide;
        }
        return photo;
    }
    public Bitmap addNoise(int noise){
        Random random=new Random();
        if(noise>=0&&noise<=100){
            for(int x=0;x<totalWide;x++)
                for(int y=0;y<totalHigh;y++){
                    int temp=Math.abs(random.nextInt()%100);
                    if(temp<noise){
                        if(photo.getPixel(x,y)==Color.BLACK)
                            photo.setPixel(x,y,Color.WHITE);
                        else
                            photo.setPixel(x,y,Color.BLACK);
                    }
                }
        }
        return  photo;
    }
}
