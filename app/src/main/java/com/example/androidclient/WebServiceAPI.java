package com.example.androidclient;

import com.example.androidclient.entities.Contact;
import com.example.androidclient.entities.ContactInvitations;
import com.example.androidclient.entities.ContactTransfer;
import com.example.androidclient.entities.Message;
import com.example.androidclient.entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceAPI {


    @GET("contacts")
    Call<List<Contact>> GetAllContacts();

    @GET("contacts/{id}")
    Call<Contact> getContact(@Path("id") String id);

    @POST("contacts")
    Call<Void> CreateContact(@Body Contact contact);

    @PUT("contacts{id}")
    Call<Void> EditContact(@Path("id") String id, @Body Contact contact);

    @DELETE("contacts/{id}")
    Call<Void> DeleteContact(@Path("id") String id);

    @GET("contacts/{id}/messages")
    Call<List<Message>> GetAllMessages(@Path("id") String id);

    @POST("contacts/{id}/messages")
    Call<Void> CreateMessage(@Path("id") String id, @Body Message message);

    @GET("contacts/{id}/messages/{id2}")
    Call<List<Message>> ViewSpecificMessage(@Path("id") String id,@Path("id2") String id2);

    @PUT("contacts/{id}/messages/{id2}")
    Call<Void> UpdateSpecificMessage(@Path("id") String id,@Path("id2") String id2, @Body Message message);

    @DELETE("contacts/{id}/messages/{id2}")
    Call<Void> UpdateSpecificMessage(@Path("id") String id,@Path("id2") String id2);

    @POST("invitations")
    Call<Void> Invitations(@Body ContactInvitations contactInvitations);

    @POST("transfer")
    Call<Void> transfer(@Body ContactTransfer contactTransfer);

    @POST("Login")
    Call<Void> Login(@Body User user);

    @POST("Register")
    Call<Void> Register(@Body User user);
}
