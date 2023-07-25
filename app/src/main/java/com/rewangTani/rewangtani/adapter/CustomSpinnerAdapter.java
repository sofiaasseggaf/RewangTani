package com.rewangTani.rewangtani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rewangTani.rewangtani.R;

public class CustomSpinnerAdapter extends BaseAdapter {

    Context context;
    int images[];
    String[] tampilan;
    LayoutInflater inflter;

    public CustomSpinnerAdapter(Context applicationContext, int[] flags, String[] tampilan) {
        this.context = applicationContext;
        this.images = flags;
        this.tampilan = tampilan;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.spinner_custom_layout, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.imageView);
        TextView names = (TextView) convertView.findViewById(R.id.textView);
        icon.setImageResource(images[position]);
        names.setText(tampilan[position]);
        return convertView;
    }
}
