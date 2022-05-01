package com.delhidairy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.delhidairy.R;
import com.delhidairy.model.addaddress.AddAddressRequest;
import com.delhidairy.model.addaddress.AddAddressResponse;
import com.delhidairy.retrofitcall.RestClient;
import com.delhidairy.utils.Constants;
import com.delhidairy.utils.DairyPrefs;
import com.delhidairy.utils.DairyUtils;
import com.delhidairy.utils.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentAddressSaveActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextCity, editTextAddress, editTextAddressTwo, editTextPhone;
    Button btnSave;

    String name1, emailId, address, addressTwo, cityname, phone, zipcode, userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        editTextName = findViewById(R.id.edittxt_payment_name);
        editTextEmail = findViewById(R.id.edittxt_payment_email);
        editTextCity = findViewById(R.id.edittxt_payment_city);
        editTextAddress = findViewById(R.id.edittxt_payment_address);
        editTextPhone = findViewById(R.id.edittxt_payment_phone);
        editTextAddressTwo= findViewById(R.id.edittxt_payment_landmark);
        btnSave = findViewById(R.id.btn_save_address);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAddressData();
            }
        });

    }

    private void saveAddressData() {

        name1 = editTextName.getText().toString().trim();
        address = editTextAddress.getText().toString().trim();

        cityname = editTextCity.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        emailId = editTextEmail.getText().toString().trim();
        addressTwo = editTextAddressTwo.getText().toString().trim();

        if (TextUtils.isEmpty(name1.trim()) || name1.length() == 0) {
            editTextName.setError("Please enter valid name!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid name!!");
            return;
        }
        if (TextUtils.isEmpty(emailId.trim()) || emailId.length() == 0) {
            editTextEmail.setError("Please enter valid email!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid email!!");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            editTextEmail.setError("Please enter valid email!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid email!!");
            return;
        }
        if (TextUtils.isEmpty(address.trim()) || address.length() == 0) {
            editTextAddress.setError("Please enter valid address!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }
        if (TextUtils.isEmpty(addressTwo.trim()) || addressTwo.length() == 0) {
            editTextAddressTwo.setError("Please enter valid address!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid address!!");
            return;
        }
        if (TextUtils.isEmpty(cityname.trim()) || cityname.length() == 0) {
            editTextCity.setError("Please enter valid city!!");
            DairyUtils.displayToast(getApplicationContext(), "Please enter valid city!!");
            return;
        }

        if (TextUtils.isEmpty(phone)) {

            editTextPhone.setError("Please enter valid mobile!!");

            return;
        } else {
            if (phone.length() < 10) {
                editTextPhone.setError("Please enter valid 10 digit Number!!");
                return;
            }
        }

        AddAddressRequest addAddressRequest = new AddAddressRequest();
        addAddressRequest.setAddress(address);
        addAddressRequest.setCity(cityname);
        addAddressRequest.setContactno(phone);
        addAddressRequest.setEmail(emailId);
        addAddressRequest.setUserid(DairyPrefs.getString(this, Constants.USER_ID));
        addAddressRequest.setFullname(name1);
        addAddressRequest.setLandmark(addressTwo);


        Util.showProgressDialog(this);
            RestClient.addnewAddress(addAddressRequest, new Callback<AddAddressResponse>() {
                @Override
                public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                    Util.dismissProgressDialog();
                    if (response.body() != null) {

                        if (response.body().getMessage().equalsIgnoreCase("Address added successsfully.")) {

                            Intent intent = new Intent(PaymentAddressSaveActivity.this,AddressForSubscriptionListActivity.class);
                            startActivity(intent);
                            Toast.makeText(PaymentAddressSaveActivity.this, "Address add successfully", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(PaymentAddressSaveActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<AddAddressResponse> call, Throwable t) {

                    Toast.makeText(PaymentAddressSaveActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
