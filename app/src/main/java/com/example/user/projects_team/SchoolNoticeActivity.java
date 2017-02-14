package com.example.user.projects_team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class SchoolNoticeActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    Context context = this;
    ListView noticeinfo;
    String name;
    String title;
    String date;
    Elements notice;
    String noticeUrl;
    int clickedItem;
    boolean iflastitem = false;
    int page = 1;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_notice);

        //화면 회전 고정 (배포시 주석제거 필요)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setTitle("학교 공지사항");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Tnotice tnotice = new Tnotice();
        tnotice.execute(null, null, null);

    }

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //TGetContent tGetContent = new TGetContent();
            //tGetContent.execute(null, null, null);
            clickedItem = i;
            noticeUrl = notice.get(clickedItem).attr("href");
            Intent intent = new Intent(SchoolNoticeActivity.this, NoticeShowActivity.class);
            intent.putExtra("Noticeurl", noticeUrl);
            startActivity(intent);
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && iflastitem) {
            page++;
            Tnotice tnotice = new Tnotice();
            tnotice.execute(null, null, null);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        iflastitem = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
    }

    class Tnotice extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);
        Elements elements;


        @Override
        protected void onPreExecute() {
            dialog.setMessage("로딩중입니다");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("http://www.dsgo.kr/main.php?menugrp=050100&master=bbs&act=list&master_sid=19&SearchCategory=&SearchColumn=&SearchValue=&Page="+page).get();
                elements = document.select("td");

                notice = document.select("td > a");
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            noticeinfo  = (ListView) findViewById(R.id.notice_view);
            ListViewAdapter adapter;
            adapter = new ListViewAdapter();

            noticeinfo.setOnItemClickListener(listener);
            noticeinfo.setAdapter(adapter);
            noticeinfo.setOnScrollListener(SchoolNoticeActivity.this);

            int i = 0;

            for (Element e: elements) {
                i ++;
                if (i == 3){
                    title = e.text();
                }

                if (i == 4){
                    name = e.text();
                }

                if (i == 5){
                    date = e.text();
                }

                if (i == 6){
                    adapter.addItem(name, title, date);
                    i = 0;
                }

            }

            dialog.dismiss();
        }

    }

    class TGetContent extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);
        Elements elements;


        @Override
        protected void onPreExecute() {
            dialog.setMessage("로딩중입니다");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*try {

            }

            catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            noticeinfo  = (ListView) findViewById(R.id.notice_view);
            ListViewAdapter adapter;
            adapter = new ListViewAdapter();

            noticeinfo.setAdapter(adapter);

            int i = 0;

            for (Element e: elements) {
                i ++;
                if (i == 3){
                    title = e.text();
                }

                if (i == 4){
                    name = e.text();
                }

                if (i == 5){
                    date = e.text();
                }

                if (i == 6){
                    adapter.addItem(name, title, date);
                    i = 0;
                }

            }

            dialog.dismiss();
        }

    }
}
