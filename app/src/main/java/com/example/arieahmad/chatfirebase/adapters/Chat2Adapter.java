package com.example.arieahmad.chatfirebase.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arieahmad.chatfirebase.R;
import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.setterGetter.Chat2SetGet;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 7/15/2017.
 */

public class Chat2Adapter extends RecyclerView.Adapter<Chat2Adapter.MyViewHolder> {

    Context ctx;
    ArrayList listItem;

    public Chat2Adapter(Context context, ArrayList arrayList) {
        this.ctx = context;
        this.listItem = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pesan_item_chat2, parent, false);
        return new MyViewHolder(view, ctx, listItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Chat2SetGet data = (Chat2SetGet) listItem.get(position);
        holder.txtNama.setText(data.getNama());
        holder.txtPesan.setText(data.getPesan());
        holder.txtWaktu.setText(data.getWaktu());

        if (data.getNama().equals(SharedPref.getInstance(ctx).getNama())){
            holder.boxPesan2.setBackground(ContextCompat.getDrawable(ctx, R.drawable.in_message_bg));
            holder.boxPesan.setGravity(Gravity.RIGHT);
            holder.boxPesan2.setPadding(16, 10, 20, 16);
        }else{
            holder.boxPesan2.setBackground(ContextCompat.getDrawable(ctx, R.drawable.out_message_bg));
            holder.boxPesan.setGravity(Gravity.LEFT);
            holder.boxPesan2.setPadding(20, 10, 16, 16);
        }

        //holder.boxPesan2.requestFocus();

        holder.boxPesan2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        holder.boxPesan2.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryLight));
                        break;
                    case MotionEvent.ACTION_UP:
                        if (data.getNama().equals(SharedPref.getInstance(ctx).getNama())){
                            holder.boxPesan2.setBackground(ContextCompat.getDrawable(ctx, R.drawable.in_message_bg));
                            holder.boxPesan2.setPadding(16, 10, 20, 16);
                        }else{
                            holder.boxPesan2.setBackground(ContextCompat.getDrawable(ctx, R.drawable.out_message_bg));
                            holder.boxPesan2.setPadding(20, 10, 16, 16);
                        }

                        break;
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout boxPesan;
        public LinearLayout boxPesan2;
        public TextView txtNama, txtPesan, txtWaktu;
        public ArrayList<Chat2SetGet> pesan = new ArrayList<>();
        public Context ctx;

        public MyViewHolder(View view, Context context, ArrayList<Chat2SetGet> psn){
            super (view);

            this.pesan = psn;
            this.ctx = context;

            boxPesan = (RelativeLayout) view.findViewById(R.id.boxPesan);
            boxPesan2 = (LinearLayout) view.findViewById(R.id.boxPesan2);
            txtNama = (TextView) view.findViewById(R.id.txtNama);
            txtPesan = (TextView) view.findViewById(R.id.txtPesan);
            txtWaktu = (TextView) view.findViewById(R.id.txtWaktu);
        }
    }
}
