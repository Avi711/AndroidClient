package com.example.androidclient.api;
import com.example.androidclient.MyApplication;
import com.example.androidclient.R;
import com.example.androidclient.WebServiceAPI;
import com.example.androidclient.entities.User;

import java.io.IOException;
import java.util.concurrent.Executors;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void login(User user) {
        Call<ResponseBody> call = webServiceAPI.Login(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                    try {
                        user.setToken("Bearer " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    user.setToken("error");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                user.setToken("error2");
            }
        });
    }

    public void register(User user) {
        Call<ResponseBody> call = webServiceAPI.Register(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200) {
                        user.setToken("ok");
                }
                else {
                    user.setToken("error");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                user.setToken("error2");
            }
        });
    }
}