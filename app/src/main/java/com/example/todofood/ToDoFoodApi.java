package com.example.todofood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ToDoFoodApi {
    @GET("items")
    Call<List<RestoranMenuPost>> getPosts();
    @GET("items/cfk")
    Call<List<RestoranMenuPost>> getPostsCFC();
    @GET("items/macdak")
    Call<List<RestoranMenuPost>> getPostsMC();
}
