package com.example.androidclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {


    @GET("contacts")
    Call<List<Contact>> getContacts();


    @POST("contacts")
    Call<List<Contact>> getContacts(@Body Contact post);

    @DELETE("contacts")
    Call<List<Contact>> getContacts(@Path("id") int id);

}
