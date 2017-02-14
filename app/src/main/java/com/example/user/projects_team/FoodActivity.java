package com.example.user.projects_team;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class FoodActivity extends AppCompatActivity {

    Context context = this;
    Button find;
    TextView bre, lun, din;
    Button datePicker;
    String years, date, month1;
    int year, month2, day;
    EditText editDate;
    Elements elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month2 = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);

        View.OnClickListener findButton = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(FoodActivity.this, dateSetListener, year, month2, day).show();
            }
        };

        /*DatePicker.OnDateChangedListener dateChangedListener = new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                month1 = String.valueOf(monthOfYear);
                date = String.valueOf(dayOfMonth);
                new Tfood().execute(null, null, null);
            }
        };*/

        //find = (Button) findViewById(R.id.food_find);

        bre = (TextView) findViewById(R.id.morning_food);
        lun = (TextView) findViewById(R.id.lunch_food);
        din = (TextView) findViewById(R.id.dinner_food);

        datePicker = (Button) findViewById(R.id.datepicker);

        //editDate = (EditText) findViewById(R.id.food_day);

        datePicker.setOnClickListener(findButton);

        //datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), dateChangedListener);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("급식");
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
        {
            years = String.valueOf(year);
            month1 = String.valueOf(monthOfYear + 1);
            date = String.valueOf(dayOfMonth);
            new Tfood().execute(null, null, null);
        }
    };

    class Tfood extends AsyncTask<Void, Void, Void> {
        String food;
        ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("로딩중입니다");
            dialog.setCancelable(false);
            dialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("http://www.dsgo.kr/main.php?menugrp=050600&master=meal2&act=list&SearchYear="+years+"&SearchMonth="+month1+"&SearchDay="+date+"#diary_list").get();
                elements = document.select("div.meal_table");

                food = elements.text();
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            int i = 0;

            for (Element e: elements) {
                i ++;
                if (i == 1){
                    bre.setText(e.text());
                }

                if (i == 2){
                    lun.setText(e.text());
                }

                if (i == 3){
                    din.setText(e.text());
                }

            }

            dialog.dismiss();
        }

    }
}


