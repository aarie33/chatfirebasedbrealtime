package com.example.arieahmad.chatfirebase.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.arieahmad.chatfirebase.R;
import com.example.arieahmad.chatfirebase.db.SharedPref;
import com.example.arieahmad.chatfirebase.setterGetter.PesanSetGet;

import java.util.ArrayList;

/**
 * Created by Arie Ahmad on 7/14/2017.
 */

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.MyViewHolder> {

    Context ctx;
    ArrayList listItem;

    public PesanAdapter(Context context, ArrayList arrayList) {
        this.ctx = context;
        this.listItem = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pesan_item, parent, false);
        return new MyViewHolder(view, ctx, listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PesanSetGet data = (PesanSetGet) listItem.get(position);
        holder.txtNama.setText(data.getNama());
        holder.txtPesan.setText(data.getPesan());
        holder.txtWaktu.setText(data.getWaktu());
        holder.imgStatus.setVisibility(View.INVISIBLE);

        if (data.getNama().equals(SharedPref.getInstance(ctx).getNama())){
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.boxPesan.setGravity(Gravity.RIGHT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.cardPesan.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryLight));
            }else{
                holder.cardPesan.setCardBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryLight));
            }
        }


    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout boxPesan;
        public CardView cardPesan;
        public TextView txtNama, txtPesan, txtWaktu;
        public ImageView imgStatus;
        public ArrayList<PesanSetGet> pesan = new ArrayList<>();
        public Context ctx;

        public MyViewHolder(View view, Context context, ArrayList<PesanSetGet> psn){
            super (view);

            this.pesan = psn;
            this.ctx = context;

            boxPesan = (RelativeLayout) view.findViewById(R.id.boxPesan);
            cardPesan = (CardView) view.findViewById(R.id.cardPesan);
            txtNama = (TextView) view.findViewById(R.id.txtNama);
            txtPesan = (TextView) view.findViewById(R.id.txtPesan);
            txtWaktu = (TextView) view.findViewById(R.id.txtWaktu);
            imgStatus = (ImageView) view.findViewById(R.id.imgStatus);
        }
    }
}
