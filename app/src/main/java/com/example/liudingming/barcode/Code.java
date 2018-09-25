/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 此为编码类，主要实现对条形码源码的编码。
  *EAN13编码规则https://wenku.baidu.com/view/2e4335fafab069dc50220128.html
 */
package com.example.liudingming.barcode;
import java.util.ArrayList;
/**
 *
 * @author LiuDingming
 */
public class Code {
    private int[][] numCode={{1,1,1,0,0,1,0},{1,1,0,0,1,1,0},{1,1,0,1,1,0,0},//EAN-13的右手边编码，左手边编码可根据右手边得出
                              {1,0,0,0,0,1,0},{1,0,1,1,1,0,0},{1,0,0,1,1,1,0},
                              {1,0,1,0,0,0,0},{1,0,0,0,1,0,0},{1,0,0,1,0,0,0},
                              {1,1,1,0,1,0,0}};
    private int[][] oddEven={{1,1,1,1,1,1},{1,1,0,1,0,0},{1,1,0,0,1,0},//左手边编码的奇偶编码表，1为奇，0为偶
                             {1,1,0,0,0,1},{1,0,1,1,0,0},{1,0,0,1,1,0},
                             {1,0,0,0,1,1},{1,0,1,0,1,0},{1,0,1,0,0,1},
                             {1,0,0,1,0,1}};
    private int[] sorce;
    private ArrayList<int[]> code;
    public  Code(int[] sorce){
        this.sorce=sorce;
    }
    public ArrayList<int[]> getCode(){//对整体进行编码
        code=new ArrayList<>();
        for(int i=0;i<12;i++){
            code.add(numCode[sorce[i+1]]);//先将所有的源码按右手边编码方式完成编码并保存。第0位决定左手边编码规则，不参与编码，从第1位开始编码，
        }
        for(int i=0;i<6;i++){              //根据第0位源码和奇偶表确定奇偶顺序，对左手边编码进行处理
            if(oddEven[sorce[0]][i]==1)
                code.set(i, oddCode(code.get(i)));//将处理后的码组替换回来的码组
            else
                code.set(i,evenCode(code.get(i)));
        }
        code.add(0,new int[]{1,0,1});
        code.add(7,new int[]{0,1,0,1,0});
        code.add(new int[]{1,0,1});//往List插入警戒位编码
        return code;
    }
    private int[] oddCode(int[] a){//奇编码函数，按位取反
        int[] temp=new int[7];
        for(int i=0;i<7;i++){
            if(a[i]==0)
                temp[i]=1;
            else temp[i]=0;}
        return temp;
    }
    private int[] evenCode(int[] a){//偶编码编码函数，前后置换
        int[] temp=new int[7];
        for(int i=0;i<7;i++)
            temp[i]=a[6-i];
        return temp;
    }
    public static int getCheck(int[] noCheck){//求检验位
        int sum=0;
        for(int temp=0;temp<12;temp++){
        if(temp%2==0)
            sum+=noCheck[temp];
        else
            sum+=noCheck[temp]*3;}
        return 10-sum%10;
    }
}
