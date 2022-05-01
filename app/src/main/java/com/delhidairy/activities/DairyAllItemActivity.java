package com.delhidairy.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.delhidairy.R;
import com.delhidairy.adapter.DairyAdapter;
import com.delhidairy.model.Readresponse;
import com.delhidairy.model.dairyallitemmodel.DairyAllItemResponse;
import com.delhidairy.retrofitcall.RestClient;
import com.delhidairy.utils.DairyUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class DairyAllItemActivity extends AppCompatActivity {
    private RecyclerView productlist;
    DairyAllItemResponse readresponse = new DairyAllItemResponse();
    private DairyAdapter adpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_all_item);

        initViews();
        getAllProduct();
    }

    private void initViews() {

        productlist = findViewById(R.id.productlist);
        productlist.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        productlist.setHasFixedSize(true);
        adpter = new DairyAdapter(readresponse.getRecords(),getApplicationContext());
        productlist.setAdapter(adpter);

        Log.d("Init", "Success");
    }


    private void getAllProduct() {

        if (DairyUtils.getConnectivityStatus(getApplicationContext())) {
            DairyUtils.showProgress(this, "Please wait...", false);
            RestClient.getAllProduct(new Callback<DairyAllItemResponse>() {
                @Override
                public void onResponse(Call<DairyAllItemResponse> call, Response<DairyAllItemResponse> response) {
                    DairyUtils.dismissDialog();

                    if (response.body() != null) {
                        readresponse = response.body();
                        adpter.setRecords(readresponse.getRecords());
                        adpter.notifyDataSetChanged();
                        adpter.setSellingListInterface(new DairyAdapter.SellingListInterface() {
                            @Override
                            public void sellinglistitem(int position) {
                                Intent i = new Intent(DairyAllItemActivity.this, ProductDescriptionActivity.class);
                                i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("author", readresponse.getRecords().get(position).getProductdescription());
                                i.putExtra("des", readresponse.getRecords().get(position).getShopImg());
                                i.putExtra("title", readresponse.getRecords().get(position).getProductdescription());
                                i.putExtra("image", readresponse.getRecords().get(position).getProductdescription());
                                startActivity(i);
                            }

                            @Override
                            public void sellinglistitemSeeAll(int position) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<DairyAllItemResponse> call, Throwable t) {
                    DairyUtils.dismissDialog();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }

    }

}

