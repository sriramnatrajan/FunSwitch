package com.ultimate.funswitch.request_connection;

import com.ultimate.funswitch.model.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ModelService {

    @GET("582695f5100000560464ca40")
    Call<List<Model>> getAllItems();
}