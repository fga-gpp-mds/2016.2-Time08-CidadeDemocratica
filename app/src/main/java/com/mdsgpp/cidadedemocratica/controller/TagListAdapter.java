package com.mdsgpp.cidadedemocratica.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mdsgpp.cidadedemocratica.R;
import com.mdsgpp.cidadedemocratica.model.Tag;
import com.mdsgpp.cidadedemocratica.model.TagListRow;

import java.util.ArrayList;

/**
 * Created by andreanmasiro on 9/21/16.
 */
public class TagListAdapter extends BaseAdapter {

    private static ArrayList<Tag> data;
    private Context context;

    public TagListAdapter(Context context, ArrayList<Tag> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TagListRow row;
        Tag currentTag = data.get(i);

        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.tag_list_row,viewGroup,false);
            row = new TagListRow();
            row.nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            view.setTag(row);

        } else {
            row = (TagListRow) view.getTag();
        }

        row.nameTextView.setText(captalizeString(currentTag.getName()));
        row.nameTextView.setTextColor(Color.BLACK);

        return view;
    }

    public void updateData(ArrayList<Tag> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private String captalizeString(String stringToCaptalize){
        return stringToCaptalize.substring(0, 1).toUpperCase() + stringToCaptalize.substring(1);
    }
}