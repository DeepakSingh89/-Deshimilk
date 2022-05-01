package com.delhidairy.retrofitcall;

import com.delhidairy.model.DairyResponse;
import com.delhidairy.model.LoginRequest;
import com.delhidairy.model.LoginResponse;
import com.delhidairy.model.addaddress.AddAddressRequest;
import com.delhidairy.model.addaddress.AddAddressResponse;
import com.delhidairy.model.dairyNamemodel.DairyNameResponse;
import com.delhidairy.model.dairyallitemmodel.DairyAllItemResponse;
import com.delhidairy.model.getAddress.GetAddressRequest;
import com.delhidairy.model.getAddress.GetAddressResponse;
import com.delhidairy.model.usercreate.Createrequest;
import com.delhidairy.model.usercreate.Createresponse;

import retrofit2.Callback;


public class RestClient {

    public static void loginUser(LoginRequest loginRequest, Callback<LoginResponse> callback) {
        RetrofitClient.getClient().login(loginRequest).enqueue(callback);

    }


    public static void getAllProduct(Callback<DairyAllItemResponse> callback) {
        RetrofitClient.getClient().getAllProduct().enqueue(callback);

    }

    public static void getAllDairy(Callback<DairyResponse> callback) {
        RetrofitClient.getClient().getAllDairy().enqueue(callback);

    }


    public static void getDairyname(Callback<DairyNameResponse> callback) {
        RetrofitClient.getClient().getAllDairyName().enqueue(callback);

    }


    public static void addnewAddress(AddAddressRequest addAddressRequest, Callback<AddAddressResponse> callback) {
        RetrofitClient.getClient().addAddress(addAddressRequest).enqueue(callback);

    }

    public static void addnewAddressget(GetAddressRequest  getAddressRequest, Callback<GetAddressResponse> callback) {
        RetrofitClient.getClient().getAddress(getAddressRequest).enqueue(callback);

    }
    public static void createNewuser(Createrequest addCreaterequest,Callback<Createresponse> callback){

        RetrofitClient.getClient().getUser(addCreaterequest).enqueue(callback);
    }



}