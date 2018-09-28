package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.kx.todaynews.R;

import java.util.List;


public class EmoJiAdapter extends ArrayAdapter<String> {


    private int type = 0;
    private final int mScreenHeigth;


    public EmoJiAdapter(int type, Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.type = type;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mScreenHeigth = dm.heightPixels;
        int  width = dm.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), R.layout.item_row_emoji, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_emoji);

       // ViewGroup.LayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // layoutParams.width = mScreenHeigth / 7;

        String fileName = getItem(position);
        Integer resId = EmoJiUtils.getEmoJiMap(type).get(fileName);
        if (resId != null) {
            Drawable drawable = getContext().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
            imageView.setImageResource(resId);
        }

        return convertView;
    }
}
