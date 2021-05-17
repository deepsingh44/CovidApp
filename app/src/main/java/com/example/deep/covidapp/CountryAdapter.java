package com.example.deep.covidapp;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private List<Country> countries;
    private Random random;
    private ClickListener clickListener;

    public CountryAdapter(List<Country> countries) {
        this.countries = countries;
        random = new Random();
    }

    public void update(List<Country> countries) {
        this.countries = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.CountryViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.ticon.setText(String.valueOf(country.getCountry().toUpperCase().charAt(0)));
        holder.tname.setText(country.getCountry());
        holder.tcase.setText(String.valueOf(country.getTodayCases()));
        GradientDrawable tvBackground = (GradientDrawable) holder.ticon.getBackground();
        tvBackground.setColor(Color.argb(255, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tname, ticon, tcase;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            tname = itemView.findViewById(R.id.countryfullname);
            ticon = itemView.findViewById(R.id.countrynamechar);
            tcase = itemView.findViewById(R.id.countrycase);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.clickItem(view, getAdapterPosition());
        }
    }

    public interface ClickListener {
        void clickItem(View view, int position);
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
