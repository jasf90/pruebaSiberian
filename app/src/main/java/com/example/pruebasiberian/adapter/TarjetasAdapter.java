package com.example.pruebasiberian.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebasiberian.R;
import com.example.pruebasiberian.model.Tarjetas;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TarjetasAdapter extends RecyclerView.Adapter<TarjetasAdapter.ViewHolder> {

    private List<Tarjetas> items;
    private Context        context;

    public TarjetasAdapter(List<Tarjetas> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View convertView = inflater.inflate(R.layout.list_tarjetas, parent,false);

        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tarjetas item = items.get(position);

        holder.tvNum.setText(item.getNumero());

        if(item.getTipo().equals("VISA")){
            Picasso.with(context)
                    .load(R.drawable.visa)
                    .rotate(0)
                    .into(holder.ivItem);
        }else if(item.getTipo().equals("MAST")){
            Picasso.with(context)
                    .load(R.drawable.mastercard)
                    .rotate(0)
                    .into(holder.ivItem);
        }else if(item.getTipo().equals("DINE"))
            Picasso.with(context)
                    .load(R.drawable.diners)
                    .rotate(0)
                    .into(holder.ivItem);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvNum;
        public ImageView ivItem;
        public ImageView ivDel;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNum  = (TextView)  itemView.findViewById(R.id.tvNum);
            ivItem = (ImageView) itemView.findViewById(R.id.ivItem);
            ivDel  = (ImageView) itemView.findViewById(R.id.ivDel);
        }
    }

}
