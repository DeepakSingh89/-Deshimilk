package com.delhidairy.home.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.delhidairy.R;

import com.delhidairy.adapter.DairyAdapter;
import com.delhidairy.model.Readresponse;
import com.delhidairy.retrofitcall.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView productlist;
    Readresponse readresponse = new Readresponse();
    private DairyAdapter adpter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(root);

        getAllProduct();
        return root;

    }


    private void initViews(View root) {

        productlist =  root.findViewById(R.id.productlist);
        productlist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        productlist.setHasFixedSize(true);
        adpter = new DairyAdapter(readresponse.getRecords(), getActivity());
        productlist.setAdapter(adpter);

        Log.d("Init","Success");
    }



    private void getAllProduct() {
        RestClient.getAllProduct(new Callback<Readresponse>() {
            @Override
            public void onResponse(Call<Readresponse> call, Response<Readresponse> response) {
                if (response.body()!=null){
                     readresponse = response.body();
                     adpter.setRecords(readresponse.getRecords());
                     adpter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Readresponse> call, Throwable t) {

            }
        });

    }

}