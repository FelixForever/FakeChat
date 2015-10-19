package com.wixappsite.fakechat.fakechat.Utils;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Wix on 2015/10/10.
 */
public class DoNet
{

    private int methodId;
    private String urlStr;
    private String paramsString;

    private  String resultStr;




    public DoNet(final int methodId, final String urlStr, final String paramsString, final OkCallBack ok,final NotOkCallBack notok) {
        System.out.println("------------------------------执行构造函数");
        this.methodId=methodId;
        this.urlStr=urlStr;
        this.paramsString = paramsString;
        this.resultStr = "";
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPostExecute(String s) {
                if (s!=null){
                    if (ok!=null){
                        System.out.println("------------------------------提示:已成功取回数据！" +s);
                        ok.OnOk(s);
                    }
                }else{
                    if (notok!=null){
                        System.out.println("------------------------------请求失败，请检查Url是否正确！" );
                        notok.OnNotOk();
                    }
                }
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                System.out.println("------------------------------正在访问："+urlStr);
                StringBuilder sb=new StringBuilder();
                try {
                    URLConnection urlc;
                    if (methodId==1){//1代  表get
                        System.out.println("------------------------------执行get方法,访问："+urlStr+"?"+paramsString);
                        urlc=new URL(urlStr+"?"+paramsString).openConnection();

                    }else{
                        System.out.println("------------------------------执行post方法,访问："+urlStr+"参数"+paramsString);
                        urlc= new URL(urlStr).openConnection();
                        urlc.setDoOutput(true);
                        urlc.setDoInput(true);
                        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(urlc.getOutputStream(),"utf-8"));
                        bw.write(paramsString);
                        bw.flush();//必须要！！！！！！

                    }
                    System.out.println("------------------------------即将读取数据.." );
                    //开始读取数据
                    BufferedReader br=new BufferedReader(new InputStreamReader(urlc.getInputStream(),"utf-8"));
                    String line;
                    int count=0;
                    while((line=br.readLine())!=null){
                        sb.append(line);
                        System.out.println("------------------------------正在读取第" + (count++) +"行");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return sb.toString();
            }
        }.execute();


    }

    public  interface OkCallBack{
        Void OnOk(String result);
    }

    public  interface NotOkCallBack{
        Void OnNotOk();
    }


}
