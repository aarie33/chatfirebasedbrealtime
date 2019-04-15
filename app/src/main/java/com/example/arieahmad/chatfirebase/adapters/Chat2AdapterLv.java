package com.example.arieahmad.chatfirebase.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arieahmad.chatfirebase.R;
import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.setterGetter.Chat2SetGet;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 7/16/2017.
 */

public class Chat2AdapterLv extends BaseAdapter {
    Activity activity;
    ArrayList listItem;

    public Chat2AdapterLv(Activity activity, ArrayList listItem) {
        this.activity = activity;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pesan_item_chat2, null);

            holder.boxPesan = (RelativeLayout) view.findViewById(R.id.boxPesan);
            holder.boxPesan2 = (LinearLayout) view.findViewById(R.id.boxPesan2);
            holder.txtNama = (TextView) view.findViewById(R.id.txtNama);
            holder.txtPesan = (TextView) view.findViewById(R.id.txtPesan);
            holder.txtWaktu = (TextView) view.findViewById(R.id.txtWaktu);

            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        final Chat2SetGet data = (Chat2SetGet) getItem(i);
        holder.txtNama.setText(data.getNama());
        holder.txtPesan.setText(data.getPesan());
        holder.txtWaktu.setText(data.getWaktu());

        if (data.getNama().equals(SharedPref.getInstance(view.getContext()).getNama())){
            holder.boxPesan2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.in_message_bg));
            holder.boxPesan.setGravity(Gravity.RIGHT);
            holder.boxPesan2.setPadding(16, 10, 20, 16);
        }else{
            holder.boxPesan2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.out_message_bg));
            holder.boxPesan.setGravity(Gravity.LEFT);
            holder.boxPesan2.setPadding(20, 10, 16, 16);
        }

        final ViewHolder hld = holder;
        holder.boxPesan2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        hld.boxPesan2.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryLight));
                        break;
                    case MotionEvent.ACTION_UP:
                        if (data.getNama().equals(SharedPref.getInstance(view.getContext()).getNama())){
                            hld.boxPesan2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.in_message_bg));
                            hld.boxPesan2.setPadding(16, 10, 20, 16);
                        }else{
                            hld.boxPesan2.setBackground(ContextCompat.getDrawable(view.getContext(), R.drawable.out_message_bg));
                            hld.boxPesan2.setPadding(20, 10, 16, 16);
                        }

                        break;
                }
                return true;
            }
        });

        return view;
    }

    static class ViewHolder{
        public RelativeLayout boxPesan;
        public LinearLayout boxPesan2;
        public TextView txtNama, txtPesan, txtWaktu;
    }
}
