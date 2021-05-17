package com.example.deep.covidapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.deep.covidapp.databinding.FragmentFirstBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private List<Country> countries = new ArrayList<>();
    private Call<List<Country>> mycall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingleTask singleTask = (SingleTask) getActivity().getApplication();
        CountryService countryService = singleTask.getRetrofit().create(CountryService.class);
        mycall = countryService.getAllCountry();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //binding.recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        CountryAdapter countryAdapter = new CountryAdapter(countries);
        countryAdapter.setOnClickListener(new CountryAdapter.ClickListener() {
            @Override
            public void clickItem(View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("country",countries.get(position));
                Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
            }
        });
        binding.recyclerview.setAdapter(countryAdapter);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mycall.enqueue(new Callback<List<Country>>() {
                    @Override
                    public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                        countries = response.body();
                        countryAdapter.update(countries);
                    }

                    @Override
                    public void onFailure(Call<List<Country>> call, Throwable t) {
                        Toast.makeText(getActivity(), "There is some problem", Toast.LENGTH_SHORT).
                                show();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}