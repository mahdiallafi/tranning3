package com.example.trainning3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter  extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    ArrayList<Contacts> arrayList;
    Activity context;
    public ContactAdapter( Activity context,ArrayList<Contacts> arrayList) {
        this.context = context;
        this.arrayList=arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
      Contacts model=arrayList.get(position);
      holder.name.setText(model.getName());
        holder.number.setText(model.getNumber());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,number;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        name=itemView.findViewById(R.id.textView);
        number=itemView.findViewById(R.id.textView2);

        }
    }
}
