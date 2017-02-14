package com.example.user.projects_team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NoticeShowActivity extends AppCompatActivity {

    String Url;
    String title;
    String name;
    String content;
    String file = "-1";

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_show);


        Intent intent = getIntent();
        Url = intent.getExtras().getString("Noticeurl");

        TgetNotice tnotice = new TgetNotice();

        tnotice.execute(null, null, null);

    }

    class TgetNotice extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(context);
        Elements elements;
        Elements fileEle;


        @Override
        protected void onPreExecute() {
            dialog.setMessage("로딩중입니다");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("http://www.dsgo.kr/" + Url).get();
                elements = document.select("td");
                fileEle = document.select("td > a");
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            TextView textView = (TextView) findViewById(R.id.texturl);
            TextView textView2 = (TextView) findViewById(R.id.texturl2);
            TextView textView3 = (TextView) findViewById(R.id.texturl3);
            TextView textView4 = (TextView) findViewById(R.id.texturl4);

            int i = 0;

            for (Element e: elements) {
                i ++;
                if (i == 1){
                    title = e.text();
                }

                if (i == 2){
                    name = e.text();
                }

                if (i == 5){
                    content = e.text();
                }

            }

            for (Element e: fileEle) {
                if (file != "-1"){
                    file = file + "\n" + e.text();
                }
                else {
                    file = e.text();
                }
            }


            textView.setText("제목: " + title);
            textView2.setText("글쓴이: " + name);
            textView3.setText("내용: " + content);
            textView4.setText("첨부파일: " + file);
            dialog.dismiss();
        }

    }
}

