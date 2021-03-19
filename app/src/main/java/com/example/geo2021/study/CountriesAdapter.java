package com.example.geo2021.study;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geo2021.ListAdapter;
import com.example.geo2021.R;
import com.example.geo2021.repository.Country;


public class CountriesAdapter extends ListAdapter<Country,CountriesAdapter.ViewHolder> {

    @Override
    protected ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected View onCreateView(@NonNull ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_item,parent,false);
    }

    @Override
    protected void onSetupView(@NonNull ViewHolder holder, int position, Country item) {
        holder.name.setText(item.name);
        //SvgLoader.pluck().with(Activity) holder.itemView.getContext()) .load(item.flag, holder.flag);
        holder.code.setText(item.alpha2Code);
        holder.itemView.setTag(R.id.item,item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            code=itemView.findViewById(R.id.code);
        }
     TextView name;
        TextView code;
        ImageView flag=itemView.findViewById(R.id.flag);
    }
}
