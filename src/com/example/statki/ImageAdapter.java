package com.example.statki;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
 
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int screenWidth;
 
    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole,
            R.drawable.pole, R.drawable.pole
    };
 
    // Constructor
    public ImageAdapter(Context c, int screenWidthParam){
        mContext = c;
        this.screenWidth = screenWidthParam;
    }
 
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
 
    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	int gridWidth = screenWidth/10;
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(gridWidth, gridWidth));
        imageView.setPadding(0, 0, 0, 0);
        return imageView;
    }
}
