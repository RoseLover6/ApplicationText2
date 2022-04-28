package com.example.applicationtext2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MyToolAcyivity<liv> extends AppCompatActivity {
    private ProgressBar mHorizonP;
    private int mProgressStatus = 0;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Integer> imageId = new ArrayList<Integer>();
        imageId.add(R.drawable.img01);
        imageId.add(R.drawable.img02);
        imageId.add(R.drawable.img03);
        imageId.add(R.drawable.img04);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        mHorizonP = (ProgressBar) findViewById(R.id.progress1);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==0x111){
                    mHorizonP.setProgress(mProgressStatus);
                }else {
                    Toast.makeText(MyToolAcyivity.this, "耗时已经完成",Toast.LENGTH_SHORT).show();
                    mHorizonP.setVisibility(View.GONE);
                    GridView gridView = (GridView) findViewById(R.id.gridview1);
                    BaseAdapter adapter = new BaseAdapter() {
                        @Override
                        public int getCount() {
                            return imageId.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            return position;
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {

                            ImageView imageView;
                            if(convertView==null){
                                imageView = new ImageView(MyToolAcyivity.this);
                                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                imageView.setPadding(5,0,5,0);
                            }else {
                                imageView=(ImageView) convertView;
                            }
                            imageView.setImageResource(imageId.get(position));
                            return imageView;
                        }
                    };
                    gridView.setAdapter(adapter);
                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                            AlertDialog alert = new AlertDialog.Builder(MyToolAcyivity.this).create();
                            alert.setIcon(R.drawable.img05);
                            alert.setTitle("系统提示");
                            alert.setMessage("确认删除此图片？");

                            alert.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog , int which) {
                                    Toast.makeText(MyToolAcyivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                                }
                            });

                            alert.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog , int which) {
                                    imageId.remove(position);
                                    adapter.notifyDataSetChanged();
                                    gridView.setAdapter(adapter);
                                    Toast.makeText(MyToolAcyivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            alert.show();
                            return true;
                        }
                    });
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProgressStatus = dowork();
                    Message m = new Message();
                    if(mProgressStatus<100){
                        m.what = 0x111;
                        mHandler.sendMessage(m);
                    }else{
                        m.what = 0x110;
                        mHandler.sendMessage(m);

                        break;
                    }
                }
            }

            private int dowork(){
                mProgressStatus+=Math.random()*10;
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                return mProgressStatus;
            }
        }).start();

    }
}
