package com.example.user.projects_team;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 2016-10-09.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListItem> listViewItemList = new ArrayList<ListItem>();

    public ListViewAdapter() {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context mcontext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.notice_title);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.notice_name);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.notice_date);

        ListItem listViewItem = listViewItemList.get(position);

        titleTextView.setText(listViewItem.getTitles());
        nameTextView.setText(listViewItem.getNames());
        dateTextView.setText(listViewItem.getNumber());

        return convertView;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(String name, String title, String date) {
        ListItem item = new ListItem();

        item.setName(name);
        item.setNumber(date);
        item.setTitle(title);

        listViewItemList.add(item);
    }
}
